package com.pca


class TwitterClientTest extends GroovyTestCase {

    private List tweets;
    private def wrapper;

    public void setUp() {
        tweets = [[tweet: "tweet 1 #include #monkey"],
                [tweet: "tweet 2"],
                [tweet: "another tweet #include"]]
        wrapper = new Expando()
        wrapper.invoked = false
        wrapper.getTweets = {
            wrapper.invoked = true;
            tweets
        }
    }

    public void test_RetrieveTweets_GivenAHashTagItRetrievesTweetsWithThatHashTag() {
        TwitterClient twitterClient = new TwitterClient(twitterWrapper: wrapper)
        assertEquals([tweets[0], tweets[2]], twitterClient.retrieveTweets("#include"))
        assertTrue(wrapper.invoked)
    }

    public void test_RetrieveTweets_givenNoHashTagItRetrievesAllTweets() {
        TwitterClient twitterClient = new TwitterClient(twitterWrapper: wrapper)
        assertEquals(tweets, twitterClient.retrieveTweets())
    }

    public void test_RetrieveTweets_givenPlainTextItRetrievesAllTweets() {
        TwitterClient twitterClient = new TwitterClient(twitterWrapper: wrapper)
        assertEquals(tweets, twitterClient.retrieveTweets("include"))
    }

}
