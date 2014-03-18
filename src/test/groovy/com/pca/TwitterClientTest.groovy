package com.pca


class TwitterClientTest extends GroovyTestCase {

    public void test_filtersTweetsByHashTag() {
        List tweets = [[hashTags: ["filtered"], tweet: "tweet 1"],
                [hashTags: ["notFiltered"], tweet: "tweet 2"]]
        TwitterClient client = new TwitterClient(tweets:  tweets);
        assertEquals([tweets[0]],
                client.filterByHashTag("filtered"))
    }

    public void test_filtersTweetsWithMultipleHashTagsReturningMultipleTweets(){
        List tweets = [[hashTags: ["filtered", "monkey"], tweet: "tweet 1"],
                [hashTags: ["notFiltered"], tweet: "tweet 2"],
                [hashTags: ["something", "filtered"], tweet: "another tweet"]]
        TwitterClient client = new TwitterClient(tweets:  tweets);
        assertEquals([tweets[0], tweets[2]], client.filterByHashTag("filtered"))
    }
}
