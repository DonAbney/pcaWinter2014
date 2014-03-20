package com.pca

class TwitterWrapperTest extends GroovyTestCase {

    void testReturnsList() {
        
        TwitterWrapper twitterWrapper = new TwitterWrapper()
        def tweets = twitterWrapper.getTweets()
        assert tweets instanceof List

    }
}
