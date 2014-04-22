package com.pca


class TwitterProcessorBlacklistTest extends GroovyTestCase {

    void testAddToListOfBlacklistedWordsAddsToWordBlacklist() {
        TwitterProcessor twitterProcessor = new TwitterProcessor()
        String badWord = "FIRETRUCK"
        twitterProcessor.blacklistWord(badWord)
        assert twitterProcessor.isWordBlacklisted(badWord)
    }

    void testAddToListOfBlacklistWordsIsCaseInsensitive() {
        TwitterProcessor twitterProcessor = new TwitterProcessor()
        String badWord1 = "FireTruck"
        String badWord2 = "FIRETruck"
        twitterProcessor.blacklistWord(badWord1)
        twitterProcessor.blacklistWord(badWord2)
        assert twitterProcessor.isWordBlacklisted("firetruck")
    }

    void testSpecialCharsDoNotBreakAddToBlacklist() {
        TwitterProcessor twitterProcessor = new TwitterProcessor()
        twitterProcessor.blacklistWord("@pples")
        twitterProcessor.blacklistWord("F1r37uck")
        assert twitterProcessor.isWordBlacklisted("F1R37UCK")
        assert twitterProcessor.isWordBlacklisted("@PPLES")
    }

    void testNumericCharactersDoNotBreakAddWordToBlacklist() {
        TwitterProcessor twitterProcessor = new TwitterProcessor()
        twitterProcessor.blacklistWord("F1r37uck")
        assert twitterProcessor.isWordBlacklisted("F1R37UCK")
    }

    void testNullBlacklistWordDoNotBreakAddWord() {
        TwitterProcessor twitterProcessor = new TwitterProcessor()
        twitterProcessor.blacklistWord(null)
        assert !twitterProcessor.isWordBlacklisted(null)
    }
}
