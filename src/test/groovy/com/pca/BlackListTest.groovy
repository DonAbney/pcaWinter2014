package com.pca

class BlackListTest extends GroovyTestCase {
    void testIsBlackListedReturnsTrueIfBlackListed() {
        def blacklist = new BlackList(handles: ['jason'] as Set)
        assertTrue(blacklist.isBlackListed(new Tweet(handle: 'jason', text: 'Hi')))
    }

    void testIsBlackListedReturnsFalseIfHandleIsNotBlackListed() {
        def blacklist = new BlackList(handles: ['aaron'])
        assertFalse(blacklist.isBlackListed(new Tweet(handle: 'jason', text: 'Hi')))
    }
}
