package com.pca

class TwitterProcessorBlacklistHandleTest extends GroovyTestCase{

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
        def handles = ['aHandle', 'handleTwo', 'yetAnotherHandle']
        TwitterProcessor processor = new TwitterProcessor()
        handles.each{ handle -> processor.blacklistHandle(handle) }
        handles.every{ handle -> assert handle in processor.blacklistedHandles }
    }

    void testUnblacklistingAHandleRemovesIt() {
        def handle = 'myHandle'
        TwitterProcessor processor = new TwitterProcessor()
        processor.blacklistHandle(handle)
        assert (handle in processor.blacklistedHandles)
        processor.unblacklistHandle(handle)
        assert !(handle in processor.blacklistedHandles)
    }

    void testUnblacklistingHandleYieldsCorrectNumberOfBlacklistedHandles()
    {
        def handles = ['firstHandle', '2ndHandle', 'handleThree']
        TwitterProcessor processor = new TwitterProcessor()
        handles.each{ handle -> processor.blacklistHandle(handle) }
        processor.unblacklistHandle(handles[1])
        assert 2 == processor.blacklistedHandles.size()
    }

    void testUnblacklistingAHandleRemovesNoOtherHandles() {
        String handle1 = 'firstHandle'
        String handle2 = '2ndHandle'
        String handle3 = 'handleThree'
        def handles = [handle1, handle2, handle3]
        TwitterProcessor processor = new TwitterProcessor()
        handles.each{ handle -> processor.blacklistHandle(handle) }
        processor.unblacklistHandle(handle2)
        assert handle1 in processor.blacklistedHandles
        assert handle3 in processor.blacklistedHandles
    }

    void testUnblacklistingALowercaseHandleRemovesUppercaseVersionsOfIt() {
        def handle = 'aHandle'
        def lowerHandle = handle.toLowerCase()
        def upperHandle = handle.toUpperCase()
        TwitterProcessor processor = new TwitterProcessor()

        processor.blacklistHandle(upperHandle)
        assert (upperHandle in processor.blacklistedHandles)
        processor.unblacklistHandle(lowerHandle)
        assert !(upperHandle in processor.blacklistedHandles)
    }

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
        TwitterProcessor processor = new TwitterProcessor()
        processor.whitelistedHandles.add(handle)
        assert (handle in processor.whitelistedHandles)
        processor.blacklistHandle(handle)
        assert !(handle in processor.whitelistedHandles)
    }

}
