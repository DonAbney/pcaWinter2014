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

    void testBlacklistingAHandleUnwhitelistsIt() {
        def handle = 'aHandle'
        TwitterProcessor processor = new TwitterProcessor()
        processor.whitelistedHandles.add(handle)
        assertTrue(handle in processor.whitelistedHandles)
        processor.blacklistHandle(handle)
        assertFalse(handle in processor.whitelistedHandles)
    }

    

}
