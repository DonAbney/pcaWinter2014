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
        assertTrue(whiteList.isHandleInList("Jason"))
    }

    void testCaseInsensitiveSearch()
    {
        assertTrue(whiteList.isHandleInList("jASON"))
    }

    void testHandleNotInList()
    {
        assertFalse(whiteList.isHandleInList("Mike"))
    }
}
