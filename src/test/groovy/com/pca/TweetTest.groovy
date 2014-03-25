package com.pca

import org.codehaus.groovy.runtime.typehandling.GroovyCastException

class TweetTest extends GroovyTestCase {
    void testCreateTweet() {
        def tweet = new Tweet(id: 7, handle: "jasonDuffy",
                text: "This is what I tweeted. #PCA #YOLO",
                hashtags: ["PCA", "YOLO"])
        assertNotNull(tweet)
    }

    void testThrowsAnExceptionWhenConstructorHasTheWrongTypeForId() {
        try {
            new Tweet(id: "five")
            fail('should have thrown a GroovyCastException')
        } catch(GroovyCastException e) {
            //expected
        }
    }

    void testCreateTweetWithSingleStringHashtag() {
        def tweet = new Tweet(id: 7, handle: "aaronmalone",
                text: "I #yolo'd", hashtags: "yolo")
        assertEquals("yolo", tweet.hashtags[0])
    }
}
