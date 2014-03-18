package com.pca

class TwitterWrapperTest extends GroovyTestCase {

    void testThatEchoMyStringReturnsMyString() {
        
        String myString = "Hello PCA"
        TwitterWrapper twitterWrapper = new TwitterWrapper()
        String actualResult = twitterWrapper.echoMyString(myString)
        assertEquals("Did not return the same string", myString,actualResult)
    }
}
