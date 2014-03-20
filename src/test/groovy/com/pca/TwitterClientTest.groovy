package com.pca


class TwitterClientTest extends GroovyTestCase {

    public void test_getLatestTweets() {
        List tweets = [[user: 'jason', tweet: 'hey everyone'], [user: 'jason', tweet: 'yo']]
        TwitterWrapper wrapper = new TwitterWrapper() {
            @Override
            List getTweets() {
                return tweets
            }
        }
        TwitterClient client = new TwitterClient(wrapper: wrapper)
        assert tweets == client.getTweets()
    }

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
}
