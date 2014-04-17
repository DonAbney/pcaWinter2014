package com.pca

class TwitterProcessorTestHandleBlacklist extends GroovyTestCase{

    void testBlacklistingANullAddsNothing() {
        String handle = null
        TwitterProcessor processor = new TwitterProcessor()
        processor.blacklistHandle(handle)
        assert 0 == processor.blacklistedHandles.size()
    }

    void testBlacklistingAnEmptyStringAddsNothing() {
        String handle = ''
        TwitterProcessor processor = new TwitterProcessor()
        processor.blacklistHandle(handle)
        assert 0 == processor.blacklistedHandles.size()
    }

    void testBlacklistingASingleHandleYieldsOneEntry() {
        String handle = 'someArbitraryHandle'
        TwitterProcessor processor = new TwitterProcessor()
        processor.blacklistHandle(handle)
        assert 1 == processor.blacklistedHandles.size()
    }

    void testBlacklistingASingleHandleAddsIt() {
        String handle = 'anArbitraryHandle'
        TwitterProcessor processor = new TwitterProcessor()
        processor.blacklistHandle(handle)
        assert handle in processor.blacklistedHandles
    }

    void testBlacklistingTheSameHandleRepeatedlyYieldsOnlyOneEntry() {
        String handle = 'aHandle'
        TwitterProcessor processor = new TwitterProcessor()
        processor.blacklistHandle(handle)
        processor.blacklistHandle(handle)
        assert 1 == processor.blacklistedHandles.size()
    }

    void testBlacklistingDifferentCapitalizationsOfTheSameHandleYieldsOnlyOneEntry() {
        String handle = 'aHandle'
        TwitterProcessor processor = new TwitterProcessor()
        processor.blacklistHandle(handle)
        processor.blacklistHandle(handle.toUpperCase())
        processor.blacklistHandle(handle.toLowerCase())
        assert 1 == processor.blacklistedHandles.size()
    }

    void testBlacklistingDistinctHandlesYieldsMultipleEntries() {
        TwitterProcessor processor = new TwitterProcessor()
        processor.blacklistHandle('aHandle')
        processor.blacklistHandle('anotherHandle')
        processor.blacklistHandle('aThirdHandle')
        assert 3 == processor.blacklistedHandles.size()
    }

    void testBlacklistingDistinctHandlesAddsEachOfThem() {
        def handles = ['aHandle', 'handle_two', 'yetAnotherHandle']
        TwitterProcessor processor = new TwitterProcessor()
        handles.each{ handle -> processor.blacklistHandle(handle) }
        handles.every{ handle -> assert handle in processor.blacklistedHandles }
    }

    void testUnblacklistingAHandleRemovesIt() {
        def handle = 'myHandle'
        TwitterProcessor processor = new TwitterProcessor()
        processor.blacklistHandle(handle)
        assertTrue(handle in processor.blacklistedHandles)
        processor.unblacklistHandle(handle)
        assertFalse(handle in processor.blacklistedHandles)
    }

    void testUnblacklistingHandleYieldsCorrectNumberOfBlacklistedHandles()
    {
        def handles = ['firstHandle', '2ndHandle', 'handleThree']
        TwitterProcessor processor = new TwitterProcessor()
        handles.each{ handle -> processor.blacklistHandle(handle) }
        processor.unblacklistHandle(handles[1])
        assertEquals(2, processor.blacklistedHandles.size())
    }

    void testUnblacklistingAHandleRemovesNoOtherHandles() {
        def handles = ['firstHandle', '2ndHandle', 'handleThree']
        TwitterProcessor processor = new TwitterProcessor()
        handles.each{ handle -> processor.blacklistHandle(handle) }
        processor.unblacklistHandle(handles[1])
        assert handles[0] in processor.blacklistedHandles
        assert handles[2] in processor.blacklistedHandles
    }

    void testUnblacklistingALowercaseHandleRemovesUppercaseVersionsOfIt() {
        def handle = 'aHandle'
        def lower_handle = handle.toLowerCase()
        def upper_handle = handle.toUpperCase()
        TwitterProcessor processor = new TwitterProcessor()

        processor.blacklistHandle(upper_handle)
        assertTrue(upper_handle in processor.blacklistedHandles)
        processor.unblacklistHandle(lower_handle)
        assertFalse(upper_handle in processor.blacklistedHandles)
    }

    void testUnblacklistingAnUppercaseHandleRemovesLowercaseVersionsOfIt() {
        def handle = 'aHandle'
        def lower_handle = handle.toLowerCase()
        def upper_handle = handle.toUpperCase()
        TwitterProcessor processor = new TwitterProcessor()

        processor.blacklistHandle(lower_handle)
        assertTrue(lower_handle in processor.blacklistedHandles)
        processor.unblacklistHandle(upper_handle)
        assertFalse(lower_handle in processor.blacklistedHandles)
    }

    void testUnblacklistingALowercaseHandleRemovesMixedcaseVersionsOfIt() {
        def handle = 'aHandle'
        def lower_handle = handle.toLowerCase()
        TwitterProcessor processor = new TwitterProcessor()

        processor.blacklistHandle(handle)
        assertTrue(handle in processor.blacklistedHandles)
        processor.unblacklistHandle(lower_handle)
        assertFalse(handle in processor.blacklistedHandles)
    }

    void testUnblacklistingAMixedcaseHandleRemovesLowercaseVersionsOfIt() {
        def handle = 'aHandle'
        def lower_handle = handle.toLowerCase()
        TwitterProcessor processor = new TwitterProcessor()

        processor.blacklistHandle(lower_handle)
        assertTrue(lower_handle in processor.blacklistedHandles)
        processor.unblacklistHandle(handle)
        assertFalse(lower_handle in processor.blacklistedHandles)

    }

    void testUnblacklistingUppercaseHandleRemovesMixedcaseVersionsOfIt() {
        def handle = 'aHandle'
        def upper_handle = handle.toUpperCase()
        TwitterProcessor processor = new TwitterProcessor()

        processor.blacklistHandle(handle)
        assertTrue(handle in processor.blacklistedHandles)
        processor.unblacklistHandle(upper_handle)
        assertFalse(handle in processor.blacklistedHandles)
    }

    void testUnblacklistingAMixedcaseHandleRemovesUppercaseVersionsOfIt() {
        def handle = 'aHandle'
        def upper_handle = handle.toUpperCase()
        TwitterProcessor processor = new TwitterProcessor()

        processor.blacklistHandle(upper_handle)
        assertTrue(upper_handle in processor.blacklistedHandles)
        processor.unblacklistHandle(handle)
        assertFalse(upper_handle in processor.blacklistedHandles)
    }

    void testBlacklistingAHandleUnwhitelistsIt() {
        def handle = 'aHandle'
        TwitterProcessor processor = new TwitterProcessor()
        processor.whitelistedHandles.add(handle)
        assertTrue(handle in processor.whitelistedHandles)
        processor.blacklistHandle(handle)
        assertFalse(handle in processor.whitelistedHandles)
    }

}
