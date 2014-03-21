package com.pca


class TwitterClientTest extends GroovyTestCase {

    private List allTweets;
    private TwitterWrapper wrapper;

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

    public void test_getTweets_givenUnusedHashTagItRetrievesAllTweets() {
        TwitterClient twitterClient = new TwitterClient(twitterWrapper: wrapper)
        assertEquals([], twitterClient.getTweets('#unused'))
    }

    public void test_getTweets_givenPlainTextItRetrievesAllTweets() {
        TwitterClient twitterClient = new TwitterClient(twitterWrapper: wrapper)
        assertEquals(allTweets, twitterClient.getTweets("include"))
    }

}
