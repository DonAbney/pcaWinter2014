package com.pca

class BlackListTest extends GroovyTestCase {

    void testIsBlackListedReturnsTrueIfBlackListed() {
        def blacklist = new BlackList(handles: ['jason'] as Set)
        assertTrue(blacklist.isBlackListed(new Tweet(handle: 'jason', text: 'Hi')))
    }

    void testIsBlackListedReturnsTrueIfHandleIsNotBlackListed() {
        def blacklist = new BlackList(handles: ['aaron'])
        assertFalse(blacklist.isBlackListed(new Tweet(handle: 'jason', text: 'Hi')))
    }

    void testCanAddWordToBlacklist() {
        BlackList blacklist = new BlackList()
        def bad_word =  'Fudge'
        blacklist.words
        assertFalse(bad_word in blacklist.words)
        blacklist.addWord(bad_word)
        assertTrue(bad_word in blacklist.words)
    }


}
