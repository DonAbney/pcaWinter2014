package com.pca

import com.pca.test.utils.TweetBuilder

class TweetTest extends GroovyTestCase {

    private TweetBuilder tweetBuilder

    void setUp() {
        tweetBuilder = new TweetBuilder()
    }

    void testNewTweetIsNotNull() {
        assertNotNull(new Tweet(id: 7, handle: "jasonDuffy",
                text: "This is what I tweeted. #PCA #YOLO",
                hashtags: ["PCA", "YOLO"]))
    }

    void testNewTweetHashtagsAreAListOfStrings() {
        def expected = ["PCA", "YOLO"]
        expected.eachWithIndex { hashtag, i ->
            assertEquals(hashtag, tweetBuilder.buildTweet(hashtags: expected).hashtags[i])
        }
    }

    void testCreateTweetWithMultipleHashtags() {
        def tweet = tweetBuilder.buildTweet(hashtags: ['ninja', 'monkey'])
        assertEquals('ninja', tweet.hashtags[0])
        assertEquals('monkey', tweet.hashtags[1])
    }
}
