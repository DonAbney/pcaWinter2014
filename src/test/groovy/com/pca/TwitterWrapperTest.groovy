package com.pca

class TwitterWrapperTest extends GroovyTestCase {

    TwitterWrapper getWrapper() {
        TwitterWrapper wrapper = new TwitterWrapper()
        wrapper
    }

    void testReturnsList() {
        assert getWrapper().getTweets() instanceof List
    }

    List getTweets() {
        getWrapper().getTweets()
    }

    void testReturnsIterableOfTweets() {
        assert getTweets().every { tweet -> tweet instanceof Tweet }
    }
}
