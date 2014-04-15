package com.pca

/**
 * Created by andrew on 4/15/14.
 */
class TwitterProcessorTest extends GroovyTestCase{

    void test_addBlackListUser_NullHandlesNotAddedToList()
    {
        String handle = null

        TwitterProcessor processor = new TwitterProcessor()
        processor.addBlackListUser(handle)

        assertEquals(0, processor.blackListUsers.size())
    }

    void test_addBlackListUser_BlacklistingASingleHandleLeadsToListOfLengthOne()
    {
        String handle = "userHandle"

        TwitterProcessor processor = new TwitterProcessor()
        processor.addBlackListUser(handle)

        assertEquals(1, processor.blackListUsers.size())
    }

    void test_addBlackListUser_BlacklistingHandleCausesItToAppearInBlacklistedHandles()
    {
        String handle = "userHandle"

        TwitterProcessor processor = new TwitterProcessor()
        processor.addBlackListUser(handle)

        assert handle in processor.blackListUsers
    }

    void test_addBlackListUser_BlackListingHandleDoesNothingIfAnyCaseOfHandleAlreadyInList()
    {
        String handle = "userHandle"

        TwitterProcessor processor = new TwitterProcessor()
        processor.addBlackListUser(handle)

        def after_first_add = processor.blackListUsers.clone()

        processor.addBlackListUser(handle.toUpperCase())
        processor.addBlackListUser(handle.toLowerCase())

        assertEquals(after_first_add, processor.blackListUsers)
    }


    void test_addBlackListUser_BlacklistingMultipleHandlesCausesAllToAppearInBlacklistedHandles()
    {
        String handle1 = "userHandle1"
        String handle2 = "AnotherFineHandle"
        String handle3 = "YetAnotherHandle"

        def handles = [handle1, handle2, handle3]

        TwitterProcessor processor = new TwitterProcessor()
        handles.each{processor.addBlackListUser(it)}

        handles.every{assert it in processor.blackListUsers}
    }

    void test_addBlackListUser_BlacklistingMultipleHandlesExtendsBlackListLength()
    {
        String handle1 = "userHandle1"
        String handle2 = "AnotherFineHandle"
        String handle3 = "YetAnotherHandle"

        def handles = [handle1, handle2, handle3]

        TwitterProcessor processor = new TwitterProcessor()
        handles.each{processor.addBlackListUser(it)}

        assertEquals(3, processor.blackListUsers.size())
    }
}
