package com.pca

import com.pca.test.utils.TweetBuilder

class TwitterClientTest extends GroovyTestCase {

    private TweetBuilder tweetBuilder

    public void setUp() {
        tweetBuilder = new TweetBuilder()
    }

    public void testGetLatestTweets() {
        def expectedTweets = []
        3.times {
            expectedTweets << tweetBuilder.buildTweet()
        }
        TwitterWrapper wrapper = getOverwrittenTwitterWrapper(expectedTweets)
        TwitterClient client = new TwitterClient(twitterWrapper: wrapper)
        List results = client.getTweets()

        assert 3 == results.size()
    }

    public void testGetTweetsFilterByTweetTextReturnsCorrectTweets() {
        def expected1 = tweetBuilder.buildTweet(text: "#tweet")
        def expected2 = tweetBuilder.buildTweet(text: "tweet")
        def unexpected = tweetBuilder.buildTweet()

        TwitterWrapper wrapper = getOverwrittenTwitterWrapper([unexpected, expected1, expected2])
        TwitterClient client = new TwitterClient(twitterWrapper: wrapper);

        def results = client.getTweetsFilterByTweetText('tweet');
        assert 2 == results.size()
        assert results.contains(expected1)
        assert results.contains(expected2)
    }

    public void testGetTweetsContainingHashTagsReturnsTweetsContainingPassedInHashTag() {
        def expectedTweet = tweetBuilder.buildTweet(hashtags: ['include'])
        TwitterWrapper wrapper = getOverwrittenTwitterWrapper([expectedTweet, tweetBuilder.buildTweet()])
        TwitterClient client = new TwitterClient(twitterWrapper: wrapper)

        def results = client.getTweetsContainingHashTags('#include');
        assert 1 == results.size()
        assert results.contains(expectedTweet)
    }

    public void testGetTweetsContainingHashTagsReturnsAllTweetsWithNullFilter() {
        def expected1 = tweetBuilder.buildTweet()
        def expected2 = tweetBuilder.buildTweet()

        TwitterWrapper wrapper = getOverwrittenTwitterWrapper([expected1, expected2])
        TwitterClient client = new TwitterClient(twitterWrapper: wrapper)

        def results = client.getTweetsContainingHashTags();
        assert results.contains(expected1)
        assert results.contains(expected2)
    }

    public void testGetTweetsContainingHashTagsReturnsNoTweetsPassingInUnusedHashTag() {
        def tweets = [tweetBuilder.buildTweet(hashtags: ['monkey', 'include'] ),
            tweetBuilder.buildTweet()]

        TwitterWrapper wrapper = getOverwrittenTwitterWrapper(tweets)
        TwitterClient client = new TwitterClient(twitterWrapper: wrapper)
        assert client.getTweetsContainingHashTags('#unused').isEmpty()
    }

    public void testGetTweetsContainingHashTagsReturnsNoTweetsPassingInvalidHashTag() {
        def tweets = [tweetBuilder.buildTweet(hashtags: ['include'] )]

        TwitterWrapper wrapper = getOverwrittenTwitterWrapper(tweets)
        TwitterClient client = new TwitterClient(twitterWrapper: wrapper)
        assertEquals([], client.getTweetsContainingHashTags('include'))
    }

    public void testGetTweetsContainingHashTagsReturnsAllTweetsPassingInEmptyFilter() {
        def expected1 = tweetBuilder.buildTweet(hashtags: ['monkey', 'include'] )
        def expected2 = tweetBuilder.buildTweet()

        TwitterWrapper wrapper = getOverwrittenTwitterWrapper([expected1, expected2])
        TwitterClient client = new TwitterClient(twitterWrapper: wrapper)
        def results = client.getTweetsContainingHashTags();

        assertEquals(2, results.size())
        assertTrue(results.contains(expected1))
        assertTrue(results.contains(expected2))
    }

    public void testWhiteListedUsersAreNotAffectedByBlackList() {
        def expected = tweetBuilder.buildTweet(handle: 'Buggs', text: 'blacklist words are bad' )

        WhiteList whiteList = new WhiteList()
        whiteList.addHandle("Buggs")
        BlackList blackList = new BlackList()
        blackList.setWords(["bad"] as Set)

        TwitterWrapper wrapper = getOverwrittenTwitterWrapper([expected])
        TwitterClient client = new TwitterClient(twitterWrapper: wrapper,
                blackList: blackList,
                whiteList: whiteList)

        List results = client.getTweetsForDisplay()

        assert results.contains(expected)
    }

    public void testNonWhiteListedUserSaysANonBlackListedWordGetsPassedThrough() {
        def expected = new Tweet (handle: 'danny', text: 'good' )

        WhiteList whiteList = new WhiteList()
        whiteList.addHandle("Buggs")
        BlackList blackList = new BlackList()
        blackList.setWords(["bad"] as Set)

        TwitterWrapper wrapper = getOverwrittenTwitterWrapper([expected])
        TwitterClient client = new TwitterClient(twitterWrapper: wrapper,
                blackList: blackList,
                whiteList: whiteList)

        List results = client.getTweetsForDisplay()

        assert results.contains(expected)
    }

    public void testNonWhiteListedUserSaysABlackListedWordGetsThrownOut()
    {
        def tweets = [tweetBuilder.buildTweet(handle: 'Buggs', text: 'I am whitelisted' )]

        WhiteList whiteList = new WhiteList()
        whiteList.addHandle("someone else")
        BlackList blackList = new BlackList()
        blackList.setWords(["whitelisted"] as Set)

        TwitterWrapper wrapper = getOverwrittenTwitterWrapper(tweets)
        TwitterClient client = new TwitterClient(twitterWrapper: wrapper,
                blackList: blackList,
                whiteList: whiteList)

        List returnedTweets = client.getTweetsForDisplay()

        assert returnedTweets.isEmpty()
    }

    public void testCanGetListOfBlackListedTweets() {
        def badTweet1 = tweetBuilder.buildTweet(handle: 'jason')
        def badTweet2 = tweetBuilder.buildTweet(handle: 'danny')
        def goodTweet = tweetBuilder.buildTweet(handle: 'aaron')

        def tweets = [badTweet1, goodTweet, badTweet2]
        def wrapper = getOverwrittenTwitterWrapper(tweets)
        def blacklist = new BlackList(handles: ['jason', 'danny'])
        def client = new TwitterClient(twitterWrapper: wrapper, blackList: blacklist)

        def actualBlackListedTweets = client.getBlackListedTweets()

        assert actualBlackListedTweets.contains(badTweet1)
        assert actualBlackListedTweets.contains(badTweet2)
        assert false == actualBlackListedTweets.contains(goodTweet)
    }

    private TwitterWrapper getOverwrittenTwitterWrapper(List tweets) {
        new TwitterWrapper() {
            @Override
            List getTweets() {
                return tweets
            }
        }
    }

    public void test_getTweets_acceptsTheBlackList()
    {
        Tweet goodTweet = new Tweet(id:0, handle: 'jason', text: 'hey everyone', hashtags: [])
        List tweets = [goodTweet]

        TwitterWrapper wrapper = new TwitterWrapper() {
            @Override
            List getTweets() {
                return tweets
            }
        }
        BlackList blackList = new BlackList();
        TwitterClient client = new TwitterClient(twitterWrapper: wrapper, blackList: blackList)

        assertEquals(goodTweet, client.getTweetsForDisplay()[0])
    }

    public void test_getTweetsForDisplay_filtersOneBadTweet()
    {
        Tweet badTweet = new Tweet(id:0, handle: 'notAWhiteListedUser', text: 'hello #booger people', hashtags: ['#booger'])
        List tweets = [badTweet]

        TwitterWrapper wrapper = new TwitterWrapper() {
            @Override
            List getTweets() {
                return tweets
            }
        }

        BlackList blackList = new BlackList();
        WhiteList whiteList = new WhiteList();
        blackList.metaClass.isBlackListed = {Tweet tweet -> true}
        TwitterClient client = new TwitterClient(twitterWrapper: wrapper, blackList: blackList, whiteList: whiteList)

        assertTrue(client.getTweetsForDisplay().isEmpty())
    }

    public void test_getTweetsForDisplay_filtersOneBadTweetWhileNotFilteringGoodTweets()
    {
        def goodTweet1 = new Tweet(id: 1, handle: 'Buggs', text: 'I am whitelisted')

        def goodTweet3 = new Tweet(id: 3, handle: 'danny', text: 'I am not whitelisted')
        def tweets = [goodTweet1,
                goodTweet3,
                new Tweet (id: 4, handle: 'danny', text: 'blacklist blacklist blacklist' ),
                new Tweet (id: 4, handle: 'danny', text: 'blah blah blah' )]

        BlackList blackList = new BlackList();
        blackList.words = ["blah", "blacklist"]
        WhiteList whiteList = new WhiteList();

        def wrapper = [getTweets: { tweets }] as TwitterWrapper
        TwitterClient client = new TwitterClient(twitterWrapper: wrapper, blackList: blackList, whiteList: whiteList)

        assertEquals(2, client.getTweetsForDisplay().size())
        assertTrue(client.getTweetsForDisplay().contains(goodTweet1))
        assertTrue(client.getTweetsForDisplay().contains(goodTweet3))
    }

}
