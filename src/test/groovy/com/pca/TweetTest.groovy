package com.pca

import org.codehaus.groovy.runtime.typehandling.GroovyCastException

class TweetTest extends GroovyTestCase {
    void testCreateTweet() {
        def tweet = new Tweet(id: 7, handle: "jasonDuffy",
                text: "This is what I tweeted. #PCA #YOLO",
                hashtags: ["PCA", "YOLO"])
        assertNotNull(tweet)
        assertTrue(tweet.hashtags instanceof List)
        assertEquals(2, tweet.hashtags.size())
        assertEquals("PCA", tweet.hashtags[0])
        assertEquals("YOLO", tweet.hashtags[1])
    }

    void testThrowsAnExceptionWhenConstructorHasTheWrongTypeForId() {
        shouldFail(GroovyCastException.class, {
            new Tweet(id: "five")
        })
    }
}
