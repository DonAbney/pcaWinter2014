package com.pca

import org.junit.Test

class TwitterClientTest extends GroovyTestCase {

    private List allTweets;
    private TwitterWrapper wrapper;
    private TwitterWrapper wrapper_forTweetText;

    public void setUp() {
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
    }

    public void test_getLatestTweets() {
        List allTweets = [new Tweet(id: 0, handle: "@user1", text: "tweet 1 #include #monkey", hashtags: ["#include", "#monkey"])]
        TwitterWrapper wrapper = new TwitterWrapper()
        wrapper.metaClass.getTweets = {
            allTweets
        }

        TwitterClient client = new TwitterClient(twitterWrapper: wrapper)
        assert allTweets == client.getTweets()
    }


    public void test_getTweets_GivenAHashTagItRetrievesTweetsWithThatHashTag() {
        List allTweets = [new Tweet(id:0, handle: "@user1", text: "tweet 1 #include #monkey", hashtags: ["#include", "#monkey"]),
                new Tweet(id: 1, handle:"@user2", text: "tweet 2", hashtags:[]),
                new Tweet(id:2, handle:"@user3", text:"another tweet #include", hashtags:[])]
        TwitterWrapper wrapper = new TwitterWrapper()

        wrapper.metaClass.getTweets = {
            allTweets
        }

        TwitterClient twitterClient = new TwitterClient(twitterWrapper: wrapper)
        assertEquals([allTweets[0], allTweets[2]], twitterClient.getTweets("#include"))
    }

    public void test_getTweets_givenNoHashTagItRetrievesAllTweets() {
        List allTweets = [new Tweet(id: 0, handle: "@user1", text: "tweet 1 #include #monkey", hashtags: ["#include", "#monkey"])]
        TwitterWrapper wrapper = new TwitterWrapper()

        wrapper.metaClass.getTweets = {
            allTweets
        }

        TwitterClient twitterClient = new TwitterClient(twitterWrapper: wrapper)

        assertEquals(allTweets, twitterClient.getTweets())
    }

    public void test_getTweets_givenUnusedHashTagItRetrievesNoTweets() {
        List allTweets = [new Tweet(id: 0, handle: "@user1", text: "tweet 1 #include #monkey", hashtags: ["#include", "#monkey"])]
        TwitterWrapper wrapper = new TwitterWrapper()
        wrapper.metaClass.getTweets = {
            allTweets
        }

        TwitterClient twitterClient = new TwitterClient(twitterWrapper: wrapper)
        assertEquals([], twitterClient.getTweets('#unused'))
    }

    public void test_getTweets_givenPlainTextItRetrievesAllTweets() {
        List allTweets = [new Tweet(id: 0, handle: "@user1", text: "tweet 1 #include #monkey", hashtags: ["#include", "#monkey"])]
        TwitterWrapper wrapper = new TwitterWrapper()
        wrapper.metaClass.getTweets = {
            allTweets
        }
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
