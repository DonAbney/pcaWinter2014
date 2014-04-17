package com.pca

class TwitterProcessorWhitelistTest extends GroovyTestCase {

    void testAddWhitelistHandle() {
        TwitterProcessor twitterProcessor = new TwitterProcessor()

        twitterProcessor.addWhiteListHandle("Elmer")

        assert twitterProcessor.whitelistedHandles.contains("Elmer")
    }

    void testAddWhitelistHandleCaseInsensitive() {
        TwitterProcessor twitterProcessor = new TwitterProcessor()

        twitterProcessor.addWhiteListHandle("eLMER")
        twitterProcessor.addWhiteListHandle("Elmer")

        assert twitterProcessor.whitelistedHandles.size() == 1
    }

    void testAddWhitelistMultipleHandles() {
        TwitterProcessor twitterProcessor = new TwitterProcessor()

        twitterProcessor.addWhiteListHandle("Elmer")
        twitterProcessor.addWhiteListHandle("Fudd")

        assert twitterProcessor.whitelistedHandles.size() == 2
    }

    void testAddWhitelistNullHandle() {
        TwitterProcessor twitterProcessor = new TwitterProcessor()

        twitterProcessor.addWhiteListHandle(null)

        assert twitterProcessor.whitelistedHandles.size() == 0
    }

    void testAddWhitelistEmptyHandle() {
        TwitterProcessor twitterProcessor = new TwitterProcessor()
        String emptyHandle = ""

        twitterProcessor.addWhiteListHandle(emptyHandle)

        assert twitterProcessor.whitelistedHandles.size() == 0
    }

    void testRemoveWhitelistHandle() {
        TwitterProcessor twitterProcessor = new TwitterProcessor()
        String handle = "Elmer"

        twitterProcessor.addWhiteListHandle(handle)
        twitterProcessor.removeWhiteListHandle(handle)

        assert twitterProcessor.whitelistedHandles.size() == 0
    }

    void testRemoveWhitelistHandleCaseInsensitive() {
        TwitterProcessor twitterProcessor = new TwitterProcessor()

        twitterProcessor.addWhiteListHandle("Elmer")
        twitterProcessor.removeWhiteListHandle("eLMER")

        assert twitterProcessor.whitelistedHandles.size() == 0
    }

    void testRemovingHandleNotInListLeavesOtherHandles() {
        TwitterProcessor twitterProcessor = new TwitterProcessor()
        twitterProcessor.addWhiteListHandle("One")
        twitterProcessor.addWhiteListHandle("Two")

        twitterProcessor.removeWhiteListHandle("Three")

        assert twitterProcessor.whitelistedHandles.size() == 2
    }

    void testRemovingLeavesOtherHandles() {
        TwitterProcessor twitterProcessor = new TwitterProcessor()
        twitterProcessor.addWhiteListHandle("One")
        twitterProcessor.addWhiteListHandle("Two")

        twitterProcessor.removeWhiteListHandle("One")

        assert twitterProcessor.whitelistedHandles.size() == 1
    }

    void testRemovingNullDoesNothing() {
        TwitterProcessor twitterProcessor = new TwitterProcessor()
        twitterProcessor.addWhiteListHandle("hi")

        twitterProcessor.removeWhiteListHandle(null)

        assert twitterProcessor.whitelistedHandles.size() == 1
    }
}
