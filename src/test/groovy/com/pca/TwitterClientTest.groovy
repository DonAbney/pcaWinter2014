package com.pca

import org.junit.Test

class TwitterClientTest extends GroovyTestCase {

    final List tweets1 = [[user:'aUserName', tweet:'no hash tags yo!!'],
            [user:'anotherUser', tweet:'a #silly tweet'],
            [user:'aUserName', tweet:'a boring tweet'] ]

    public void test_filtersTweetsByHashTag() {
        List tweets = [[hashTags: ["filtered"], tweet: "tweet 1"],
                [hashTags: ["notFiltered"], tweet: "tweet 2"]]
        TwitterClient client = new TwitterClient(tweets:  tweets);
        assertEquals([tweets[0]],
                client.filterByHashTag("filtered"))
    }

    public void testShouldReturnEmptyListWhenSuppliedHashtagDoesNotMatchHashtagAssignedToTweet() {
        def tweet1 = [hashTags: ["dabney"], tweet: "tweet 1"]
        def tweet2 = [hashTags: ["notFiltered"], tweet: "tweet 2"]
        List tweets = [tweet1, tweet2]

        TwitterClient client = new TwitterClient(tweets:  tweets);

        assertEquals([], client.filterByHashTag("ddaugher"))
    }

    // how do I find/filter a tweet if the tweet does not have a hashtag?
    // what happens if zero tweets exist for the hashtag I am filtering by?
    // is it possible to filter by 'null' hashtag? ... or should a value always be required?
    // can I filter by multiple hashtags at the same time?
    // the filterByHashTag is returning a MAP... could this be refactored?

    public void test_filtersTweetsWithMultipleHashTagsReturningMultipleTweets(){
        List tweets = [[hashTags: ["filtered", "monkey"], tweet: "tweet 1"],
                [hashTags: ["notFiltered"], tweet: "tweet 2"],
                [hashTags: ["something", "filtered"], tweet: "another tweet"]]
        TwitterClient client = new TwitterClient(tweets:  tweets);
        assertEquals([tweets[0], tweets[2]], client.filterByHashTag("filtered"))
    }

    public void test_filterByTweetText_returnsList()
    {
        TwitterClient client = new TwitterClient();

        def tweets = client.getTweetsFilterByTweetText("");
        assertTrue(tweets instanceof List);
    }


    public void test_filterByTweetText_ReturnsEmptyListWhenFilterTermIsNull()
    {
        TwitterClient client = new TwitterClient();

        def tweets = client.getTweetsFilterByTweetText(null);
        assertEquals(tweets.size(), 0);
    }

    public void test_filterByTweetText_returnsEmptyList()
    {
        TwitterClient client = new TwitterClient();

        def tweets = client.getTweetsFilterByTweetText("");
        assertEquals(tweets.size(), 0);
    }

    public void test_filterByTweetText_returnsSomethingWhenExpected()
    {
        TwitterClient client = new TwitterClient(tweets:  tweets1);

        def tweets = client.getTweetsFilterByTweetText("tweet");
        assertTrue(tweets.size() >= 1);
    }

    public void test_filterByTweetText_returnsCorrectTweets()
    {
        TwitterClient client = new TwitterClient(tweets:  tweets1);

        def tweets = client.getTweetsFilterByTweetText("tweet");
        assertTrue(tweets.size() == 2);
        assertTrue(tweets.any{tweet -> tweet.tweet == 'a #silly tweet'});
        assertTrue(tweets.any{tweet -> tweet.tweet == 'a boring tweet'});
    }

    public void test_getTweets_givenPlainTextItRetrievesAllTweets() {
        TwitterClient twitterClient = new TwitterClient(twitterWrapper: wrapper)
        assertEquals(allTweets, twitterClient.getTweets("include"))
    }
}
