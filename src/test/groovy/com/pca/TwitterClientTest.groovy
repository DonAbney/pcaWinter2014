package com.pca

class TwitterClientTest extends GroovyTestCase {

    public void test_getLatestTweets() {
        def expected1 = new Tweet(id: 1, handle: 'jason', text: 'hey everyone')
        def expected2 = new Tweet(id: 2, handle: 'jason', text: 'yo')
        def expected3 = new Tweet(id: 3, handle: 'sleepy', text: 'look, I got a #hashtag', hashtags: ['hashtag'])

        TwitterWrapper wrapper = getOverwrittenTwitterWrapper([expected1, expected2, expected3])
        TwitterClient client = new TwitterClient(twitterWrapper: wrapper)
        List results = client.getTweets()

        assertEquals(3, results.size())
        assertTrue(results.contains(expected1))
        assertTrue(results.contains(expected2))
        assertTrue(results.contains(expected3))
    }

    public void test_getTweetsFilterByTweetText_returnsAllTweetsWhenFilterIsEmptyString() {
        def expected1 = new Tweet(id: 1, handle: 'aUserName', text: 'no hash tags yo!!')
        def expected2 = new Tweet(id: 2, handle: 'anotherUser', text: 'a #silly tweet', hashtags: ['silly'])
        def expected3 = new Tweet(id: 3, handle: 'aUserName', text: 'a boring tweet')

        TwitterWrapper wrapper = getOverwrittenTwitterWrapper([expected1, expected2, expected3])
        TwitterClient client = new TwitterClient(twitterWrapper: wrapper);
        List results = client.getTweets()

        assertEquals(3, results.size())
        assertTrue(results.contains(expected1))
        assertTrue(results.contains(expected2))
        assertTrue(results.contains(expected3))
    }

    public void test_getTweetsFilterByTweetText_ReturnsAllTweetsWithNullFilter() {
        def expected1 = new Tweet(id: 1, handle: 'aUserName', text: 'no hash tags yo!!')
        def expected2 = new Tweet(id: 2, handle: 'anotherUser', text: 'a #silly tweet', hashtags: ['silly'])
        def expected3 = new Tweet(id: 3, handle: 'aUserName', text: 'a boring tweet')

        TwitterWrapper wrapper = getOverwrittenTwitterWrapper([expected1, expected2, expected3])
        TwitterClient client = new TwitterClient(twitterWrapper: wrapper);
        List results = client.getTweets()

        assertEquals(3, results.size())
        assertTrue(results.contains(expected1))
        assertTrue(results.contains(expected2))
        assertTrue(results.contains(expected3))
    }

    public void test_getTweetsFilterByTweetText_ReturnsCorrectTweets() {
        def expected1 = new Tweet(id: 2, handle: 'anotherUser', text: 'a #silly tweet', hashtags: ['silly'])
        def expected2 = new Tweet(id: 3, handle: 'aUserName', text: 'a boring tweet')
        def unexpected = new Tweet(id: 1, handle: 'aUserName', text: 'no hash tags yo!!')


        TwitterWrapper wrapper = getOverwrittenTwitterWrapper([unexpected, expected1, expected2])
        TwitterClient client = new TwitterClient(twitterWrapper: wrapper);

        def results = client.getTweetsFilterByTweetText('tweet');
        assertEquals(2, results.size());
        assertTrue(results.contains(expected1))
        assertTrue(results.contains(expected2))
    }

    public void test_getTweetsContainingHashTags_ReturnsTweetsContainingPassedInHashTag() {
        def expected1 = new Tweet (id: 1, handle: 'someHandle', text: 'tweet 1 #include #monkey', hashtags: ['monkey', 'include'] )
        def expected2 = new Tweet (id: 3, handle: 'someHandle', text: 'another tweet #include', hashtags: ['include'] )
        def unexpected1 = new Tweet (id: 2, handle: 'anotherHandle', text: 'tweet 2' )
        def unexpected2 = new Tweet (id: 4, handle: 'lastHandle', text: 'tweet without include hashtag' )
        def unexpected3 = new Tweet (id: 5, handle: 'lastHandle', text: 'tweet with #in part of previous tweet', hashtags: ['in'] )


        TwitterWrapper wrapper = getOverwrittenTwitterWrapper([expected1, expected2, unexpected1, unexpected2, unexpected3])
        TwitterClient client = new TwitterClient(twitterWrapper: wrapper)

        def results = client.getTweetsContainingHashTags('#include');
        assertEquals(2, results.size())
        assertTrue(results.contains(expected1))
        assertTrue(results.contains(expected2))
    }

    public void test_getTweetsContainingHashTags_ReturnsAllTweetsWithNullFilter() {
        def expected1 = new Tweet (id: 1, handle: 'someHandle', text: 'tweet 1 #include #monkey', hashtags: ['monkey', 'include'] )
        def expected2 = new Tweet (id: 2, handle: 'anotherHandle', text: 'tweet 2' )
        def expected3 = new Tweet (id: 3, handle: 'someHandle', text: 'another tweet #include', hashtags: ['include'] )
        def expected4 = new Tweet (id: 4, handle: 'lastHandle', text: 'tweet without include hashtag' )

        TwitterWrapper wrapper = getOverwrittenTwitterWrapper([expected1, expected2, expected3, expected4])
        TwitterClient client = new TwitterClient(twitterWrapper: wrapper)

        def results = client.getTweetsContainingHashTags();
        assertEquals(4, results.size())
        assertTrue(results.contains(expected1))
        assertTrue(results.contains(expected2))
        assertTrue(results.contains(expected3))
        assertTrue(results.contains(expected4))
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
        BlackList blackList = new BlackList()

        TwitterWrapper wrapper = getOverwrittenTwitterWrapper(tweets)
        TwitterClient client = new TwitterClient(twitterWrapper: wrapper,
                blackList: blackList,
                whiteList: whiteList)

        List returnedTweets = client.getTweetsForDisplay()

        assertFalse(returnedTweets.contains(tweets[3]))
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

        assertEquals(goodTweet, client.getTweets()[0])
    }

    public void test_getTweets_filtersOneBadTweet()
    {
        Tweet badTweet = new Tweet(id:0, handle: 'jason', text: 'hello #booger people', hashtags: ['#booger'])
        List tweets = [badTweet]

        TwitterWrapper wrapper = new TwitterWrapper() {
            @Override
            List getTweets() {
                return tweets
            }
        }

        BlackList blackList = new BlackList();
        blackList.metaClass.isBlackListed = {Tweet tweet -> true}
        TwitterClient client = new TwitterClient(twitterWrapper: wrapper, blackList: blackList)

        assertTrue(client.getTweets().isEmpty())
    }
}
