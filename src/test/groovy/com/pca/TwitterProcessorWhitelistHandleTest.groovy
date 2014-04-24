package com.pca

class TwitterProcessorWhitelistHandleTest extends GroovyTestCase {

    void testAddWhitelistHandle() {
        TwitterProcessor twitterProcessor = new TwitterProcessor()

        twitterProcessor.whitelistHandle("Elmer")

        assert twitterProcessor.whitelistedHandles.contains("Elmer")
    }

    void testAddWhitelistHandleCaseInsensitive() {
        TwitterProcessor twitterProcessor = new TwitterProcessor()

        twitterProcessor.whitelistHandle("eLMER")
        twitterProcessor.whitelistHandle("Elmer")

        assert twitterProcessor.whitelistedHandles.size() == 1
    }

    void testAddWhitelistMultipleHandles() {
        TwitterProcessor twitterProcessor = new TwitterProcessor()

        twitterProcessor.whitelistHandle("Elmer")
        twitterProcessor.whitelistHandle("Fudd")

        assert twitterProcessor.whitelistedHandles.size() == 2
    }

    void testAddWhitelistNullHandle() {
        TwitterProcessor twitterProcessor = new TwitterProcessor()

        twitterProcessor.whitelistHandle(null)

        assert twitterProcessor.whitelistedHandles.size() == 0
    }

    void testAddWhitelistEmptyHandle() {
        TwitterProcessor twitterProcessor = new TwitterProcessor()
        String emptyHandle = ""

        twitterProcessor.whitelistHandle(emptyHandle)

        assert twitterProcessor.whitelistedHandles.size() == 0
    }

    void testunwhitelistHandle() {
        TwitterProcessor twitterProcessor = new TwitterProcessor()
        String handle = "Elmer"

        twitterProcessor.whitelistHandle(handle)
        twitterProcessor.unwhitelistHandle(handle)

        assert twitterProcessor.whitelistedHandles.size() == 0
    }

    void testunwhitelistHandleCaseInsensitive() {
        TwitterProcessor twitterProcessor = new TwitterProcessor()

        twitterProcessor.whitelistHandle("Elmer")
        twitterProcessor.unwhitelistHandle("eLMER")

        assert twitterProcessor.whitelistedHandles.size() == 0
    }

    void testRemovingHandleNotInListLeavesOtherHandles() {
        TwitterProcessor twitterProcessor = new TwitterProcessor()
        twitterProcessor.whitelistHandle("One")
        twitterProcessor.whitelistHandle("Two")

        twitterProcessor.unwhitelistHandle("Three")

        assert twitterProcessor.whitelistedHandles.size() == 2
    }

    void testRemovingLeavesOtherHandles() {
        TwitterProcessor twitterProcessor = new TwitterProcessor()
        twitterProcessor.whitelistHandle("One")
        twitterProcessor.whitelistHandle("Two")

        twitterProcessor.unwhitelistHandle("One")

        assert twitterProcessor.whitelistedHandles.size() == 1
    }

    void testRemovingNullDoesNothing() {
        TwitterProcessor twitterProcessor = new TwitterProcessor()
        twitterProcessor.whitelistHandle("hi")

        twitterProcessor.unwhitelistHandle(null)

        assert twitterProcessor.whitelistedHandles.size() == 1
    }

    void testAddingToTheWhitelistRemovesFromTheBlacklist() {
        TwitterProcessor twitterProcessor = new TwitterProcessor()
        String handle = "PersonWithAFace"
        twitterProcessor.blacklistHandle(handle)
        twitterProcessor.whitelistHandle(handle)

        assert twitterProcessor.blacklistedHandles.size() == 0
    }
}
