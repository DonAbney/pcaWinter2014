package com.pca

class WhiteListTest extends GroovyTestCase
{
    WhiteList whiteList  // white list used by tests


    void setUp()
    {
        whiteList = new WhiteList()
    }

    void testHandleIsInList()
    {
        assertTrue(whiteList.isHandleInList("Jason"))
    }

    void testCaseInsensitiveSearch()
    {
        assert(whiteList.isHandleInList("jASON"))
    }

    void testHandleNotInList()
    {
        assertFalse(whiteList.isHandleInList("Mike"))
    }
}
