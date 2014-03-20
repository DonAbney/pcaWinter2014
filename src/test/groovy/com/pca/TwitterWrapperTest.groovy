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

    void testReturnsIterableOfMaps() {
        getTweets().each { tweet -> assert tweet instanceof Map }
    }

    void testReturnsIterableOfMapsWithExpectedKeys() {
        def expectedKeys = ['hashtags', 'user', 'tweet'] as Set;
        assert getTweets().every { tweet -> expectedKeys == tweet.keySet() }
    }

    void testHashTagsAsList() {
        assert getTweets().every { tweet -> tweet.hashtags instanceof List }
    }

    void testUserAsString() {
        assert getTweets().every { tweet -> tweet.user instanceof String }
    }

    void testTweetAsString() {
        assert getTweets().every { tweet -> tweet.tweet instanceof String }
    }

    void testEveryHashTagBeginsWithPoundSign() {
        assert getTweets().every { tweet -> tweet.hashtags.every { it.startsWith('#') }}
    }

    void testNoBlankUsers() {
        assert getTweets().every { tweet -> tweet.user.size() > 0 }
    }

    void testNoBlankTweets() {
        assert getTweets().every { tweet -> tweet.tweet.size() > 0 }
    }

}
