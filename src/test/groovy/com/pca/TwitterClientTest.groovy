package com.pca

class TwitterClientTest extends GroovyTestCase {

    public void test_getLatestTweets() {

        def tweets = [new Tweet(id: 1, handle: 'jason', text: 'hey everyone'),
                new Tweet(id: 2, handle: 'jason', text: 'yo'),
                new Tweet(id: 3, handle: 'sleepy', text: 'look, I got a #hashtag', hashtags: ['hashtag'])]

        TwitterWrapper wrapper = getOverwrittenTwitterWrapper(tweets)
        TwitterClient client = new TwitterClient(twitterWrapper: wrapper)
        assertEquals(tweets, client.getTweets())
    }

    public void test_getTweetsFilterByTweetText_returnsAllTweetsWhenFilterIsEmptyString() {
        def tweets = [new Tweet(id: 1, handle: 'aUserName', text: 'no hash tags yo!!'),
                new Tweet(id: 2, handle: 'anotherUser', text: 'a #silly tweet', hashtags: ['silly']),
                new Tweet(id: 3, handle: 'aUserName', text: 'a boring tweet')]

        TwitterWrapper wrapper = getOverwrittenTwitterWrapper(tweets)
        TwitterClient client = new TwitterClient(twitterWrapper: wrapper);
        assertEquals(tweets, client.getTweetsFilterByTweetText(''));
    }

    public void test_getTweetsFilterByTweetText_ReturnsAllTweetsWithNullFilter() {
        def tweets = [new Tweet(id: 1, handle: 'aUserName', text: 'no hash tags yo!!'),
                new Tweet(id: 2, handle: 'anotherUser', text: 'a #silly tweet', hashtags: ['silly']),
                new Tweet(id: 3, handle: 'aUserName', text: 'a boring tweet')]

        TwitterWrapper wrapper = getOverwrittenTwitterWrapper(tweets)
        TwitterClient client = new TwitterClient(twitterWrapper: wrapper);
        assertEquals(tweets, client.getTweetsFilterByTweetText())
    }

    public void test_getTweetsFilterByTweetText_ReturnsCorrectTweets() {
        def tweets = [new Tweet(id: 1, handle: 'aUserName', text: 'no hash tags yo!!'),
                new Tweet(id: 2, handle: 'anotherUser', text: 'a #silly tweet', hashtags: ['silly']),
                new Tweet(id: 3, handle: 'aUserName', text: 'a boring tweet')]

        TwitterWrapper wrapper = getOverwrittenTwitterWrapper(tweets)
        TwitterClient client = new TwitterClient(twitterWrapper: wrapper);
        def retrievedTweets = client.getTweetsFilterByTweetText('tweet');
        assertTrue(retrievedTweets.size() == 2);
        assertEquals(tweets[1], retrievedTweets[0])
        assertEquals(tweets[2], retrievedTweets[1])
    }

    public void test_getTweetsContainingHashTags_ReturnsTweetsContainingPassedInHashTag() {
        def tweets = [new Tweet (id: 1, handle: 'someHandle', text: 'tweet 1 #include #monkey', hashtags: ['monkey', 'include'] ),
                new Tweet (id: 2, handle: 'anotherHandle', text: 'tweet 2' ),
                new Tweet (id: 3, handle: 'someHandle', text: 'another tweet #include', hashtags: ['include'] ),
                new Tweet (id: 4, handle: 'lastHandle', text: 'tweet without include hashtag' ),
                new Tweet (id: 4, handle: 'lastHandle', text: 'tweet with #in part of previous tweet', hashtags: ['in'] )]

        TwitterWrapper wrapper = getOverwrittenTwitterWrapper(tweets)
        TwitterClient client = new TwitterClient(twitterWrapper: wrapper)
        assertEquals([tweets[0], tweets[2]], client.getTweetsContainingHashTags('#include'))
    }

    public void test_getTweetsContainingHashTags_ReturnsAllTweetsWithNoFilter() {
        def tweets = [new Tweet (id: 1, handle: 'someHandle', text: 'tweet 1 #include #monkey', hashtags: ['monkey', 'include'] ),
                new Tweet (id: 2, handle: 'anotherHandle', text: 'tweet 2' ),
                new Tweet (id: 3, handle: 'someHandle', text: 'another tweet #include', hashtags: ['include'] ),
                new Tweet (id: 4, handle: 'lastHandle', text: 'tweet without include hashtag' )]

        TwitterWrapper wrapper = getOverwrittenTwitterWrapper(tweets)
        TwitterClient client = new TwitterClient(twitterWrapper: wrapper)
        assertEquals(tweets, client.getTweetsContainingHashTags())
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

    public void test_getTweetsContainingHashTags_ReturnsAllTweetsPassingInEmptyString() {
        def tweets = [new Tweet (id: 1, handle: 'someHandle', text: 'tweet 1 #include #monkey', hashtags: ['monkey', 'include'] ),
                new Tweet (id: 2, handle: 'anotherHandle', text: 'tweet 2' ),
                new Tweet (id: 3, handle: 'someHandle', text: 'another tweet #include', hashtags: ['include'] ),
                new Tweet (id: 4, handle: 'lastHandle', text: 'tweet without include hashtag' ),
                new Tweet (id: 4, handle: 'lastHandle', text: 'tweet with #in part of previous tweet', hashtags: ['in'] )]

        TwitterWrapper wrapper = getOverwrittenTwitterWrapper(tweets)
        TwitterClient client = new TwitterClient(twitterWrapper: wrapper)
        assertEquals(tweets, client.getTweetsContainingHashTags(''))
    }








    public void testWhiteListedUsersAreNotAffectedByBlackList()
    {
        def tweets = [new Tweet (id: 1, handle: 'Buggs', text: 'I am whitelisted' ),
                new Tweet (id: 2, handle: 'Buggs', text: 'blacklist words are bad' ),
                new Tweet (id: 3, handle: 'danny', text: 'I am not whitelisted' ),
                new Tweet (id: 4, handle: 'danny', text: 'blacklist blacklist blacklist' )]

        WhiteList whiteList = new WhiteList()
        BlackList blackList = new BlackList()

        TwitterWrapper wrapper = getOverwrittenTwitterWrapper(tweets)
        TwitterClient client = new TwitterClient(twitterWrapper: wrapper,
                blackList: blackList,
                whiteList: whiteList)

        List returnedTweets = client.getTweetsForDisplay()

        assertTrue(returnedTweets.contains(tweets[0]))
        assertTrue(returnedTweets.contains(tweets[1]))
    }

    public void testNonWhiteListedUserSaysANonBlackListedWordGetsPassedThrough()
    {
        def tweets = [new Tweet (id: 1, handle: 'Buggs', text: 'I am whitelisted' ),
                new Tweet (id: 2, handle: 'Buggs', text: 'blacklist words are bad' ),
                new Tweet (id: 3, handle: 'danny', text: 'I am not whitelisted' ),
                new Tweet (id: 4, handle: 'danny', text: 'blacklist blacklist blacklist' )]

        WhiteList whiteList = new WhiteList()
        BlackList blackList = new BlackList()

        TwitterWrapper wrapper = getOverwrittenTwitterWrapper(tweets)
        TwitterClient client = new TwitterClient(twitterWrapper: wrapper,
                blackList: blackList,
                whiteList: whiteList)

        List returnedTweets = client.getTweetsForDisplay()

        assertTrue(returnedTweets.contains(tweets[2]))
    }

    public void testNonWhiteListedUserSaysABlackListedWordGetsThrownOut()
    {
      def goodWhiteList1 = new Tweet (id: 1, handle: 'Buggs', text: 'I am whitelisted' )
      def goodWhiteList2 = new Tweet (id: 2, handle: 'Buggs', text: 'blacklist words are bad' )
      def goodNotInBlackList3 = new Tweet (id: 3, handle: 'danny', text: 'I am not whitelisted' )
      def badBlackList4 = new Tweet (id: 4, handle: 'danny', text: 'blacklist blacklist blacklist' )

      def tweets = [goodWhiteList1, goodWhiteList2, goodNotInBlackList3, badBlackList4]

        def whiteList = new WhiteList()
        def blackList = new BlackList()

        def wrapper = getOverwrittenTwitterWrapper(tweets)
        def client = new TwitterClient(twitterWrapper: wrapper,
                blackList: blackList,
                whiteList: whiteList)

        List returnedTweets = client.getTweetsForDisplay()

      assert 3 == returnedTweets.size()
      [goodWhiteList1, goodWhiteList2, goodNotInBlackList3].each { goodTweet ->
        assert returnedTweets.find {it == goodTweet}
      }
    }

    private TwitterWrapper getOverwrittenTwitterWrapper(List tweets) {
        new TwitterWrapper() {
            @Override
            List getTweets() {
                return tweets
            }
        }
    }
}
