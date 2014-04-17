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

        assertEquals(3, results.size())
    }

    public void testGetTweetsFilterByTweetTextReturnsAllTweetsWhenFilterIsEmptyString() {
        def expectedTweets = []
        3.times {
            expectedTweets << tweetBuilder.buildTweet()
        }

        TwitterWrapper wrapper = getOverwrittenTwitterWrapper(expectedTweets)
        TwitterClient client = new TwitterClient(twitterWrapper: wrapper);
        assert 3 == client.getTweets().size()
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
        assertEquals(1, results.size())
        assertTrue(results.contains(expectedTweet))
    }

    public void test_getTweetsContainingHashTags_ReturnsAllTweetsWithNullFilter() {
        def expected1 = tweetBuilder.buildTweet()
        def expected2 = tweetBuilder.buildTweet()

        TwitterWrapper wrapper = getOverwrittenTwitterWrapper([expected1, expected2])
        TwitterClient client = new TwitterClient(twitterWrapper: wrapper)

        def results = client.getTweetsContainingHashTags();
        assertTrue(results.contains(expected1))
        assertTrue(results.contains(expected2))
    }

    public void test_getTweetsContainingHashTags_ReturnsNoTweetsPassingInUnusedHashTag() {
        def tweets = [new Tweet (id: 1, handle: 'someHandle', text: 'tweet 1 #include #monkey', hashtags: ['monkey', 'include'] ),
                new Tweet (id: 2, handle: 'anotherHandle', text: 'tweet 2' ),
                new Tweet (id: 3, handle: 'someHandle', text: 'another tweet #include', hashtags: ['include'] ),
                new Tweet (id: 4, handle: 'lastHandle', text: 'tweet without include hashtag' )]

        TwitterWrapper wrapper = getOverwrittenTwitterWrapper(tweets)
        TwitterClient client = new TwitterClient(twitterWrapper: wrapper)
        assertEquals([], client.getTweetsContainingHashTags('#unused'))
    }

    public void test_getTweetsContainingHashTags_ReturnsNoTweetsPassingInvalidHashTag() {
        def tweets = [new Tweet (id: 1, handle: 'someHandle', text: 'tweet 1 #include #monkey', hashtags: ['monkey', 'include'] ),
                new Tweet (id: 2, handle: 'anotherHandle', text: 'tweet 2' ),
                new Tweet (id: 3, handle: 'someHandle', text: 'another tweet #include', hashtags: ['include'] ),
                new Tweet (id: 4, handle: 'lastHandle', text: 'tweet without include hashtag' ),
                new Tweet (id: 4, handle: 'lastHandle', text: 'tweet with #in part of previous tweet', hashtags: ['in'] )]

        TwitterWrapper wrapper = getOverwrittenTwitterWrapper(tweets)
        TwitterClient client = new TwitterClient(twitterWrapper: wrapper)
        assertEquals([], client.getTweetsContainingHashTags('include'))
    }

    public void test_getTweetsContainingHashTags_ReturnsAllTweetsPassingInEmptyFilter() {
        def expected1 = new Tweet (id: 1, handle: 'someHandle', text: 'tweet 1 #include #monkey', hashtags: ['monkey', 'include'] )
        def expected2 = new Tweet (id: 2, handle: 'anotherHandle', text: 'tweet 2' )
        def expected3 = new Tweet (id: 3, handle: 'someHandle', text: 'another tweet #include', hashtags: ['include'] )
        def expected4 = new Tweet (id: 4, handle: 'lastHandle', text: 'tweet without include hashtag' )
        def expected5 = new Tweet (id: 4, handle: 'lastHandle', text: 'tweet with #in part of previous tweet', hashtags: ['in'] )

        TwitterWrapper wrapper = getOverwrittenTwitterWrapper([expected1, expected2, expected3, expected4, expected5])
        TwitterClient client = new TwitterClient(twitterWrapper: wrapper)
        def results = client.getTweetsContainingHashTags();

        assertEquals(5, results.size())
        assertTrue(results.contains(expected1))
        assertTrue(results.contains(expected2))
        assertTrue(results.contains(expected3))
        assertTrue(results.contains(expected4))
        assertTrue(results.contains(expected5))
    }

    public void testWhiteListedUsersAreNotAffectedByBlackList() {
        def expected1 = new Tweet (id: 1, handle: 'Buggs', text: 'I am whitelisted' )
        def expected2 = new Tweet (id: 2, handle: 'Buggs', text: 'blacklist words are bad' )
        def otherTweet1 = new Tweet (id: 3, handle: 'danny', text: 'I am not whitelisted' )
        def otherTweet2 = new Tweet (id: 4, handle: 'danny', text: 'blacklist blacklist blacklist' )

        WhiteList whiteList = new WhiteList()
        whiteList.addHandle("Buggs")
        BlackList blackList = new BlackList()


        TwitterWrapper wrapper = getOverwrittenTwitterWrapper([expected1, otherTweet1, expected2, otherTweet2])
        TwitterClient client = new TwitterClient(twitterWrapper: wrapper,
                blackList: blackList,
                whiteList: whiteList)

        List results = client.getTweetsForDisplay()

        assertTrue(results.contains(expected1))
        assertTrue(results.contains(expected2))
    }

    public void testNonWhiteListedUserSaysANonBlackListedWordGetsPassedThrough() {
        def expected = new Tweet (id: 3, handle: 'danny', text: 'I am not whitelisted' )
        def otherTweet1 = new Tweet (id: 1, handle: 'Buggs', text: 'I am whitelisted' )
        def otherTweet2 = new Tweet (id: 2, handle: 'Buggs', text: 'blacklist words are bad' )
        def otherTweet3 = new Tweet (id: 4, handle: 'danny', text: 'blacklist blacklist blacklist' )

        WhiteList whiteList = new WhiteList()
        whiteList.addHandle("Buggs")
        BlackList blackList = new BlackList()

        TwitterWrapper wrapper = getOverwrittenTwitterWrapper([expected, otherTweet1, otherTweet2, otherTweet3])
        TwitterClient client = new TwitterClient(twitterWrapper: wrapper,
                blackList: blackList,
                whiteList: whiteList)

        List results = client.getTweetsForDisplay()

        assertTrue(results.contains(expected))
    }

    public void testNonWhiteListedUserSaysABlackListedWordGetsThrownOut()
    {
        def tweets = [new Tweet (id: 1, handle: 'Buggs', text: 'I am whitelisted' ),
                new Tweet (id: 2, handle: 'Buggs', text: 'blacklist words are bad' ),
                new Tweet (id: 3, handle: 'danny', text: 'I am not whitelisted' ),
                new Tweet (id: 4, handle: 'danny', text: 'blacklist blacklist blacklist' )]

        WhiteList whiteList = new WhiteList()
        whiteList.addHandle("Buggs")
        BlackList blackList = new BlackList()

        TwitterWrapper wrapper = getOverwrittenTwitterWrapper(tweets)
        TwitterClient client = new TwitterClient(twitterWrapper: wrapper,
                blackList: blackList,
                whiteList: whiteList)

        List returnedTweets = client.getTweetsForDisplay()

        assertFalse(returnedTweets.contains(tweets[3]))
    }

    public void testCanGetListOfBlackListedTweets() {
        def badTweet1 = new Tweet(handle: 'jason', text: 'hello')
        def badTweet2 = new Tweet(handle: 'danny', text: 'I have no idea')
        def goodTweet = new Tweet(handle: 'aaron', text: 'goodbye')
        def tweets = [badTweet1, goodTweet, badTweet2]
        def wrapper = new TwitterWrapper() {
            @Override
            List getTweets() {
                tweets
            }
        }
        def blacklist = new BlackList(handles: ['jason', 'danny'])
        def client = new TwitterClient(twitterWrapper: wrapper, blackList: blacklist)

        assertTrue([badTweet1, badTweet2].every {
            client.getBlackListedTweets().contains(it)
        })
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
