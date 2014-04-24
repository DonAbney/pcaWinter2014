package com.pca

import com.pca.test.utils.TweetBuilder

class TwitterProcessorWhitelistHandleTest extends GroovyTestCase {

    void testAddWhitelistHandle() {
        TwitterProcessor twitterProcessor = new TwitterProcessor()
        String handle = "Elmer"
        Tweet tweet = new TweetBuilder().buildTweet(handle: handle )

        twitterProcessor.whitelistHandle(handle)

        assert twitterProcessor.whitelistedHandleFilter.isWhitelisted(tweet)
    }

    void testunwhitelistHandle() {
        TwitterProcessor twitterProcessor = new TwitterProcessor()
        String handle = "Elmer"
        Tweet tweet = new TweetBuilder().buildTweet(handle: handle)
        twitterProcessor.whitelistHandle(handle)

        twitterProcessor.unwhitelistHandle(handle)

        assert !twitterProcessor.whitelistedHandleFilter.isWhitelisted(tweet)
    }

    void testAddingToTheWhitelistRemovesFromTheBlacklist() {
        TwitterProcessor twitterProcessor = new TwitterProcessor()
        String handle = "PersonWithAFace"
        twitterProcessor.blacklistHandle(handle)
        twitterProcessor.whitelistHandle(handle)

        assert twitterProcessor.blacklistedHandles.size() == 0
    }
}
