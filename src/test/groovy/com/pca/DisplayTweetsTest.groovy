package com.pca

class TweetDisplayTest extends GroovyTestCase {

    TweetDisplay getDisplay() {
        TweetDisplay display = new TweetDisplay()
        display
    }

    void testGivenListReturnsString() {
        assert getDisplay().renderTweetsToHTML([]) instanceof String
    }

}
