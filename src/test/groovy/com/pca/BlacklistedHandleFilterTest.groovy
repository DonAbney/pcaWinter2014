package com.pca

import com.pca.test.utils.TweetBuilder

class BlacklistedHandleFilterTest extends GroovyTestCase {

    void testBlacklistingANullAddsNothing() {
        String handle = null
        Tweet tweet = new TweetBuilder().buildTweet(handle: handle)

        BlacklistedHandleFilter filter = new BlacklistedHandleFilter()
        filter.blacklistHandle(handle)
        assert !filter.isBlacklisted(tweet)
    }

    void testBlacklistingAnEmptyStringBlacklistsNothing() {
        String handle = ''
        Tweet tweet = new TweetBuilder().buildTweet(handle: handle)

        BlacklistedHandleFilter filter = new BlacklistedHandleFilter()
        filter.blacklistHandle(handle)
        assert !filter.isBlacklisted(tweet)
    }

    void testBlacklistingASingleHandleBlacklistsTweetWithHandle() {
        String handle = 'someArbitraryHandle'
        Tweet tweet = new TweetBuilder().buildTweet(handle: handle)

        BlacklistedHandleFilter filter = new BlacklistedHandleFilter()
        filter.blacklistHandle(handle)
        assert filter.isBlacklisted(tweet)
    }

    void testUnblacklistingAHandleNoLongerBlacklistsTweetWithHandle() {
        def handle = 'myHandle'
        Tweet tweet = new TweetBuilder().buildTweet(handle: handle)
        BlacklistedHandleFilter filter = new BlacklistedHandleFilter()
        filter.blacklistHandle(handle)
        filter.unblacklistHandle(handle)
        assert !filter.isBlacklisted(tweet)
    }

    void testUnblacklistingAHandleDoesNotAffectOtherBlacklistedHandles() {
        String handle1 = 'firstHandle'
        String handle2 = '2ndHandle'
        String handle3 = 'handleThree'
        Tweet tweet1 = new TweetBuilder().buildTweet(handle: handle1)
        Tweet tweet2 = new TweetBuilder().buildTweet(handle: handle2)
        Tweet tweet3 = new TweetBuilder().buildTweet(handle: handle3)

        def tweets = [tweet1, tweet2, tweet3]
        BlacklistedHandleFilter filter = new BlacklistedHandleFilter()
        tweets.each{ tweet -> filter.blacklistHandle(tweet.handle) }
        filter.unblacklistHandle(tweet2.handle)
        assert filter.isBlacklisted(tweet1)
        assert filter.isBlacklisted(tweet3)
    }

    void testBlacklistedHandleComparisonToTweetHandleIsCaseInsensitive() {
        String lowercaseHandle = 'ahandle'
        String uppercaseHandle = lowercaseHandle.toUpperCase()
        Tweet tweet = new TweetBuilder().buildTweet(handle: uppercaseHandle)

        BlacklistedHandleFilter filter = new BlacklistedHandleFilter()
        filter.blacklistHandle(lowercaseHandle)
        assert filter.isBlacklisted(tweet)
    }

    void testUnblacklistingALowercaseHandleUnblacklistsUppercaseVersionsOfIt() {
        String handle = 'aHandle'
        String lowercaseHandle = handle.toLowerCase()
        String uppercaseHandle = handle.toUpperCase()

        Tweet tweet = new TweetBuilder().buildTweet(handle: handle)
        BlacklistedHandleFilter filter = new BlacklistedHandleFilter()

        filter.blacklistHandle(lowercaseHandle)
        assert filter.isBlacklisted(tweet)

        filter.unblacklistHandle(uppercaseHandle)
        assert !filter.isBlacklisted(tweet)
    }

    /*
    void testUnblacklistingAnUppercaseHandleRemovesLowercaseVersionsOfIt() {
        def handle = 'aHandle'
        def lowerHandle = handle.toLowerCase()
        def upperHandle = handle.toUpperCase()
        TwitterProcessor processor = new TwitterProcessor()

        processor.blacklistHandle(lowerHandle)
        assert (lowerHandle in processor.blacklistedHandles)
        processor.unblacklistHandle(upperHandle)
        assert !(lowerHandle in processor.blacklistedHandles)
    }

    void testUnblacklistingALowercaseHandleRemovesMixedcaseVersionsOfIt() {
        def handle = 'aHandle'
        def lowerHandle = handle.toLowerCase()
        TwitterProcessor processor = new TwitterProcessor()

        processor.blacklistHandle(handle)
        assert (handle in processor.blacklistedHandles)
        processor.unblacklistHandle(lowerHandle)
        assert !(handle in processor.blacklistedHandles)
    }

    void testUnblacklistingAMixedcaseHandleRemovesLowercaseVersionsOfIt() {
        def handle = 'aHandle'
        def lowerHandle = handle.toLowerCase()
        TwitterProcessor processor = new TwitterProcessor()

        processor.blacklistHandle(lowerHandle)
        assert (lowerHandle in processor.blacklistedHandles)
        processor.unblacklistHandle(handle)
        assert !(lowerHandle in processor.blacklistedHandles)

    }

    void testUnblacklistingUppercaseHandleRemovesMixedcaseVersionsOfIt() {
        def handle = 'aHandle'
        def upperHandle = handle.toUpperCase()
        TwitterProcessor processor = new TwitterProcessor()

        processor.blacklistHandle(handle)
        assert (handle in processor.blacklistedHandles)
        processor.unblacklistHandle(upperHandle)
        assert !(handle in processor.blacklistedHandles)
    }

    void testUnblacklistingAMixedcaseHandleRemovesUppercaseVersionsOfIt() {
        def handle = 'aHandle'
        def upperHandle = handle.toUpperCase()
        TwitterProcessor processor = new TwitterProcessor()

        processor.blacklistHandle(upperHandle)
        assert (upperHandle in processor.blacklistedHandles)
        processor.unblacklistHandle(handle)
        assert !(upperHandle in processor.blacklistedHandles)
    }

    void testBlacklistingAHandleUnwhitelistsIt() {
        def handle = 'aHandle'
        Tweet tweet = new TweetBuilder().buildTweet(handle: handle)

        TwitterProcessor processor = new TwitterProcessor()
        processor.whitelistHandle(handle)
        processor.blacklistHandle(handle)

        assert !(processor.whitelistedHandleFilter.isWhitelisted(tweet))
    }

    void testUnblacklistingNullDoesNothing() {
        TwitterProcessor processor = new TwitterProcessor()

        processor.blacklistHandle("hi")
        processor.unblacklistHandle(null)

        assert processor.blacklistedHandles.size() == 1
    }*/
}
