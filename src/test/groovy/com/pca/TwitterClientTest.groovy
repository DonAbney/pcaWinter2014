package com.pca

import com.pca.test.utils.TweetBuilder

class TwitterClientTest extends GroovyTestCase {

    private TweetBuilder tweetBuilder

    public void setUp() {
        tweetBuilder = new TweetBuilder()
    }

    private TwitterWrapper getOverwrittenTwitterWrapper(List tweets) {
        TwitterWrapper wrapper = new TwitterWrapper()
        wrapper.metaClass.getTweets = { tweets }
        wrapper
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

    public void testGetTweetsAcceptsTheBlackList()
    {
        Tweet goodTweet = tweetBuilder.buildTweet()
        List tweets = [goodTweet]

        TwitterWrapper wrapper = getOverwrittenTwitterWrapper(tweets)
        BlackList blackList = new BlackList();
        TwitterClient client = new TwitterClient(twitterWrapper: wrapper, blackList: blackList)

        assert goodTweet == client.getTweetsForDisplay()[0]
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
        assert client.getTweetsContainingHashTags('include').isEmpty()
    }

    public void testGetTweetsContainingHashTagsReturnsAllTweetsPassingInEmptyFilter() {
        def expected1 = tweetBuilder.buildTweet(hashtags: ['monkey', 'include'] )
        def expected2 = tweetBuilder.buildTweet()

        TwitterWrapper wrapper = getOverwrittenTwitterWrapper([expected1, expected2])
        TwitterClient client = new TwitterClient(twitterWrapper: wrapper)
        def results = client.getTweetsContainingHashTags();

        assert 2 == results.size()
        assert results.contains(expected1)
        assert results.contains(expected2)
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

    public void testGetTweetsForDisplayFiltersOneBadTweet()
    {
        Tweet badTweet = tweetBuilder.buildTweet(text: 'hello #booger people')
        List tweets = [badTweet]

        TwitterWrapper wrapper = getOverwrittenTwitterWrapper(tweets)

        BlackList blackList = new BlackList();
        blackList.setWords(["booger"] as Set)
        TwitterClient client = new TwitterClient(twitterWrapper: wrapper, blackList: blackList, whiteList: new WhiteList())

        assert client.getTweetsForDisplay().isEmpty()
    }

    public void testGetTweetsForDisplayFiltersOneBadTweetWhileNotFilteringGoodTweets()
    {
        def goodTweet1 = tweetBuilder.buildTweet()
        def badTweet = tweetBuilder.buildTweet(text: 'blacklist')
        def tweets = [goodTweet1, badTweet]

        BlackList blackList = new BlackList();
        blackList.words = ["blacklist"]

        def wrapper = getOverwrittenTwitterWrapper(tweets)
        TwitterClient client = new TwitterClient(twitterWrapper: wrapper, blackList: blackList, whiteList: new WhiteList())

        assertEquals(1, client.getTweetsForDisplay().size())
        assertTrue(client.getTweetsForDisplay().contains(goodTweet1))
    }
}
