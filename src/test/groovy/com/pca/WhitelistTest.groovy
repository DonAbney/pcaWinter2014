package com.pca

class WhitelistTest extends GroovyTestCase
{
    def whiteList

    void setUp()
    {
        whiteList = new WhiteList()
    }

    void testIsInWhitelist()
    {
        assert(whiteList.isInWhiteList("Jason"))
    }

    void testIsNotInWhitelist()
    {
        assertFalse(whiteList.isInWhiteList("Mike"))
    }
}
