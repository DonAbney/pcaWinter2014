package com.pca

import com.pca.test.utils.TweetBuilder


class WhitelistedHandleFilterTest extends GroovyTestCase
{
    void testAddWhitelistHandle() {
        WhitelistedHandleFilter filter = new WhitelistedHandleFilter()

        filter.whitelistHandle("Elmer")

        assert filter.whitelistedHandles.contains("Elmer")
    }

    void testAddWhitelistHandleCaseInsensitive() {
        WhitelistedHandleFilter filter = new WhitelistedHandleFilter()

        filter.whitelistHandle("eLMER")
        filter.whitelistHandle("Elmer")

        assert filter.whitelistedHandles.size() == 1
    }

    void testAddWhitelistMultipleHandles() {
        WhitelistedHandleFilter filter = new WhitelistedHandleFilter()

        filter.whitelistHandle("Elmer")
        filter.whitelistHandle("Fudd")

        assert filter.whitelistedHandles.size() == 2
    }

    void testAddWhitelistNullHandle() {
        WhitelistedHandleFilter filter = new WhitelistedHandleFilter()

        filter.whitelistHandle(null)

        assert filter.whitelistedHandles.size() == 0
    }

    void testAddWhitelistEmptyHandle() {
        WhitelistedHandleFilter filter = new WhitelistedHandleFilter()
        String emptyHandle = ""

        filter.whitelistHandle(emptyHandle)

        assert filter.whitelistedHandles.size() == 0
    }

    void testunwhitelistHandle() {
        WhitelistedHandleFilter filter = new WhitelistedHandleFilter()
        String handle = "Elmer"

        filter.whitelistHandle(handle)
        filter.unwhitelistHandle(handle)

        assert filter.whitelistedHandles.size() == 0
    }

    void testunwhitelistHandleCaseInsensitive() {
        WhitelistedHandleFilter filter = new WhitelistedHandleFilter()

        filter.whitelistHandle("Elmer")
        filter.unwhitelistHandle("eLMER")

        assert filter.whitelistedHandles.size() == 0
    }

    void testRemovingHandleNotInListLeavesOtherHandles() {
        WhitelistedHandleFilter filter = new WhitelistedHandleFilter()
        filter.whitelistHandle("One")
        filter.whitelistHandle("Two")

        filter.unwhitelistHandle("Three")

        assert filter.whitelistedHandles.size() == 2
    }

    void testRemovingLeavesOtherHandles() {
        WhitelistedHandleFilter filter = new WhitelistedHandleFilter()
        filter.whitelistHandle("One")
        filter.whitelistHandle("Two")

        filter.unwhitelistHandle("One")

        assert filter.whitelistedHandles.size() == 1
    }

    void testRemovingNullDoesNothing() {
        WhitelistedHandleFilter filter = new WhitelistedHandleFilter()
        filter.whitelistHandle("hi")

        filter.unwhitelistHandle(null)

        assert filter.whitelistedHandles.size() == 1
    }

    void testWhitelistedHandleIsIdentified()
    {
        WhitelistedHandleFilter filter = new WhitelistedHandleFilter()
        filter.whitelistHandle("Elmer")
        Tweet tweet = new TweetBuilder().buildTweet(handle : "Elmer")
        assert(filter.isWhitelisted(tweet))
    }

    void testNotWhitelistedHandleIsNotIdentified()
    {
        WhitelistedHandleFilter filter = new WhitelistedHandleFilter()
        filter.whitelistHandle("Elmer")
        Tweet tweet = new TweetBuilder().buildTweet(handle : "BrUceWayne")
        assert(!filter.isWhitelisted(tweet))
    }

    void testWhitelistedHandleIdentificationIsCaseInsensitive()
    {
        WhitelistedHandleFilter filter = new WhitelistedHandleFilter()
        filter.whitelistHandle("Elmer")
        Tweet tweet = new TweetBuilder().buildTweet(handle : "ELMER")
        assert(filter.isWhitelisted(tweet))
    }
}
