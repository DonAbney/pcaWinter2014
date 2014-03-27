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
        shouldFail(GroovyCastException.class, {
            new Tweet(id: "five")
        })
    }

    void testCreateTweetWithMultipleHashtags() {
        def tweet = new Tweet(id: 76, handle: "ninjaMonkey",
                text: "MONKEY POWER!!! #ninja #monkey", hashtags: ['ninja', 'monkey'])
        assertEquals('ninja', tweet.hashtags[0])
        assertEquals('monkey', tweet.hashtags[1])
    }
}
