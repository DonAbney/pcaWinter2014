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

    void testUserAsString() {
        assert getTweets().every { tweet -> tweet.handle instanceof String }
    }

    void testTweetAsString() {
        assert getTweets().every { tweet -> tweet.text instanceof String }
    }

    void testNoBlankUsers() {
        assert getTweets().every { tweet -> tweet.handle.size() > 0 }
    }

    void testNoBlankTweets() {
        assert getTweets().every { tweet -> tweet.text.size() > 0 }
    }
}
