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

    public void test_getTweets_acceptsTheBlackList()
    {
        Tweet goodTweet = new Tweet(id:0, handle: 'jason', text: 'hey everyone', hashtags: [])
        List tweets = [goodTweet]//,
        //new Tweet(id:1, handle: 'fred', text: 'this test is #crap', hashtags: ["#crap"]),
        //new Tweet(id:2, handle: 'derek', text: 'hey everyone', hashtags: [])]

        //[user: 'jason', tweet: 'hey everyone'], [user: 'jason', tweet: 'yo']]
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
}
