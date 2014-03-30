package com.pca

import org.junit.Test

class TwitterClientTest extends GroovyTestCase {

    private List allTweets;
    private TwitterWrapper wrapper;
    private TwitterWrapper wrapper_forTweetText;
    private TwitterWrapper wrapper_forWhiteListBlackList

    private Tweet whitelisted1
    private Tweet whitelisted2
    private Tweet notWhitelisted1
    private Tweet notWhitelisted2
    private WhiteList whiteList
    private BlackList blackList

    public void setUp() {
        whiteList = new WhiteList()
        blackList = new BlackList()

        allTweets = [[tweet: "tweet 1 #include #monkey"],
                [tweet: "tweet 2"],
                [tweet: "another tweet #include"]]
        wrapper = new TwitterWrapper() {
            @Override
            List getTweets() {
                allTweets
            }

        }

        wrapper_forTweetText = new TwitterWrapper() {
            @Override
            List getTweets() {
                [[user:'aUserName', tweet:'no hash tags yo!!'],
                        [user:'anotherUser', tweet:'a #silly tweet'],
                        [user:'aUserName', tweet:'a boring tweet'] ]
            }
        }

        whitelisted1 = new Tweet(handle: "Buggs", text: "I am whitelisted")
        whitelisted2 = new Tweet(handle: "Buggs", text: "blacklist words are bad")
        notWhitelisted1 = new Tweet(handle: "danny", text: "I am not whitelisted")
        notWhitelisted2 = new Tweet(handle: "danny", text: "blacklist blacklist blacklist")

        wrapper_forWhiteListBlackList = new TwitterWrapper() {
            @Override
            List getTweets() {
                [whitelisted1, whitelisted2, notWhitelisted1, notWhitelisted2]
            }
        }
    }

    public void test_getLatestTweets() {
        List tweets = [[user: 'jason', tweet: 'hey everyone'], [user: 'jason', tweet: 'yo']]
        TwitterWrapper wrapper = new TwitterWrapper() {
            @Override
            List getTweets() {
                return tweets
            }
        }
        TwitterClient client = new TwitterClient(twitterWrapper: wrapper)
        assert tweets == client.getTweets()
    }


    public void test_getTweets_GivenAHashTagItRetrievesTweetsWithThatHashTag() {
        TwitterClient twitterClient = new TwitterClient(twitterWrapper: wrapper)
        assertEquals([allTweets[0], allTweets[2]], twitterClient.getTweets("#include"))
    }

    public void test_getTweets_givenNoHashTagItRetrievesAllTweets() {
        TwitterClient twitterClient = new TwitterClient(twitterWrapper: wrapper)
        assertEquals(allTweets, twitterClient.getTweets())
    }

    public void test_getTweets_givenUnusedHashTagItRetrievesNoTweets() {
        TwitterClient twitterClient = new TwitterClient(twitterWrapper: wrapper)
        assertEquals([], twitterClient.getTweets('#unused'))
    }

    public void test_getTweets_givenPlainTextItRetrievesAllTweets() {
        TwitterClient twitterClient = new TwitterClient(twitterWrapper: wrapper)
        assertEquals(allTweets, twitterClient.getTweets("include"))
    }

    public void test_filterByTweetText_returnsAllTweetsWhenFilterIsEmptyString()
    {
        TwitterClient client = new TwitterClient(twitterWrapper: wrapper_forTweetText);

        def tweets = client.getTweetsFilterByTweetText("");
        assertEquals(3, tweets.size());
    }

    public void test_filterByTweetText_returnsSomethingWhenExpected()
    {
        TwitterClient client = new TwitterClient(twitterWrapper: wrapper_forTweetText);

        def tweets = client.getTweetsFilterByTweetText("tweet");
        assertTrue(tweets.size() >= 1);
    }

    public void test_filterByTweetText_returnsCorrectTweets()
    {
        TwitterClient client = new TwitterClient(twitterWrapper: wrapper_forTweetText);

        def tweets = client.getTweetsFilterByTweetText("tweet");
        assertTrue(tweets.size() == 2);
        assertTrue(tweets.any{tweet -> tweet.tweet == 'a #silly tweet'});
        assertTrue(tweets.any{tweet -> tweet.tweet == 'a boring tweet'});
    }

    public void testWhiteListedUsersAreNotAffectedByBlackList()
    {
        //when a white listed user who says a black listed word it gets passed through without hindrance
        TwitterClient uut = new TwitterClient(twitterWrapper: wrapper_forWhiteListBlackList,
                blackList: blackList,
                whiteList: whiteList)

        List tweets = uut.getTweetsForDisplay()

        assertTrue(tweets.contains(whitelisted1))
        assertTrue(tweets.contains(whitelisted2))
    }

    public void testNonWhiteListedUserSaysANonBlackListedWordGetsPassedThrough()
    {
        TwitterClient uut = new TwitterClient(twitterWrapper: wrapper_forWhiteListBlackList,
                blackList: blackList,
                whiteList: whiteList)

        List tweets = uut.getTweetsForDisplay()

        assertTrue(tweets.contains(notWhitelisted1))
    }

    public void testNonWhiteListedUserSaysABlackListedWordGetsThrownOut()
    {
        TwitterClient uut = new TwitterClient(twitterWrapper: wrapper_forWhiteListBlackList,
                blackList: blackList,
                whiteList: whiteList)

        List tweets = uut.getTweetsForDisplay()

        assertFalse(tweets.contains(notWhitelisted2))
    }

    public void testCanGetListOfBlackListedTweets() {
        def tweets = [
                new Tweet(handle: 'jason', text: 'hello'),
                new Tweet(handle: 'aaron', text: 'goodbye')
        ]
        def wrapper = new TwitterWrapper() {
            @Override
            List getTweets() {
                tweets
            }
        }
        def blacklist = new BlackList(handles: ['jason'])
        def client = new TwitterClient(twitterWrapper: wrapper, blackList: blacklist)
        assert [tweets[0]] == client.getBlackListedTweets()
    }
}
