package com.pca

class WhiteListTest extends GroovyTestCase
{
    WhiteList whiteList


    void setUp()
    {
        whiteList = new WhiteList()
    }

    void testHandleIsInList()
    {
        whiteList.addHandle("Jason")

        assertTrue(whiteList.isHandleInList("Jason"))
    }

    void testCaseInsensitiveSearch()
    {
        whiteList.addHandle("Jason")

        assertTrue(whiteList.isHandleInList("jASON"))
    }

    void testHandleNotInList()
    {
        whiteList.addHandle("Jason")

        assertFalse(whiteList.isHandleInList("Mike"))
    }

    void test_isHandleInList_FailsGracefullyFromNullHandle()
    {
        assertFalse(whiteList.isHandleInList(null))
    }

    void test_isHandleInList_MulitpleHandles()
    {
        whiteList.addHandle("Elmer")
        whiteList.addHandle("Buggs")

        assertTrue(whiteList.isHandleInList("Elmer"))
        assertTrue(whiteList.isHandleInList("Buggs"))
    }
}
