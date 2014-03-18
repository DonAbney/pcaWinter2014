package com.pca


class TweetTest extends GroovyTestCase {

    public void test_filtersTweetsByHashTag() {
        List tweets = [[hashTags: ["filtered"], tweet: "tweet 1"],
                [hashTags: ["notFiltered"], tweet: "tweet 2"]]
        TwitterClient client = new TwitterClient(tweets:  tweets);
        assertEquals([[hashTags: ["filtered"], tweet: "tweet 1"]],
                client.filterByHashTag("filtered"))
    }

//    public void test_filtersTweetsWithMultipleHashTags(){
//        List tweets = [[hashTags: ["filtered", "monkey"], tweet: "tweet 1"],
//                [hashTags: ["notFiltered"], tweet: "tweet 2"],
//                [hashTags: ]]
//        TwitterClient client = new TwitterClient(tweets:  tweets);
//        assertEquals([[hashTags: ["filtered"], tweet: "tweet 1"]],
//                client.filterByHashTag("filtered"))
//    }
}
