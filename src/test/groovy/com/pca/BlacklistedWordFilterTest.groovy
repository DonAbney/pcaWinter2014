package com.pca

import com.pca.test.utils.TweetBuilder

/**
 * Created by andrew on 4/24/14.
 */
class BlacklistedWordFilterTest extends GroovyTestCase {


    void testAddToListOfBlacklistedWordsAddsToWordBlacklist() {
        BlacklistedWordFilter filter = new BlacklistedWordFilter()
        String badWord = "FIRETRUCK"

        filter.blacklistWord(badWord)

        assert filter.blacklistedWords.contains(badWord)
    }

    void testAddToListOfBlacklistWordsIsCaseInsensitive() {
        BlacklistedWordFilter filter = new BlacklistedWordFilter()
        String badWord1 = "FireTruck"
        String badWord2 = "FIRETruck"

        filter.blacklistWord(badWord1)
        filter.blacklistWord(badWord2)

        assert filter.blacklistedWords.contains("firetruck")
    }


    void testSpecialCharsDoNotBreakAddToBlacklist() {
        BlacklistedWordFilter filter = new BlacklistedWordFilter()

        filter.blacklistWord("@pples")
        filter.blacklistWord("F1r37uck")

        assert filter.blacklistedWords.contains("F1R37UCK")
        assert filter.blacklistedWords.contains("@PPLES")
    }

    void testNumericCharactersDoNotBreakAddWordToBlacklist() {
        BlacklistedWordFilter filter = new BlacklistedWordFilter()

        filter.blacklistWord("F1r37uck")

        assert filter.blacklistedWords.contains("F1R37UCK")
    }

    void testNullBlacklistWordDoNotBreakAddWord() {
        BlacklistedWordFilter filter = new BlacklistedWordFilter()

        filter.blacklistWord(null)

        assert !filter.blacklistedWords.contains(null)
    }

    void testUnblacklistingWordRemovesWordFromList()
    {
        BlacklistedWordFilter filter = new BlacklistedWordFilter()
        String badWord = "FIRETRUCK"

        filter.blacklistWord(badWord)
        filter.unblacklistWord(badWord)

        assert !filter.blacklistedWords.contains(badWord)
    }

    void testUnblacklistingAWordIsCaseInsensitive() {
        BlacklistedWordFilter filter = new BlacklistedWordFilter()
        String badWord1 = "FireTruck"
        String badWord2 = "FIRETruck"

        filter.blacklistWord(badWord1)
        filter.unblacklistWord(badWord2)

        assert !filter.blacklistedWords.contains(badWord1)
    }

    void testTweetWithBlacklistedWordIsIdentifiedAsBlacklisted()
    {
        BlacklistedWordFilter filter = new BlacklistedWordFilter()
        String badWord = "FIRETRUCK"
        Tweet tweet = new TweetBuilder().buildTweet(text: badWord)

        filter.blacklistWord(badWord)

        assert filter.isBlacklisted(tweet)
    }

    void testTweetWithTextContainingBlacklistedWordIsIdentifiedAsBlacklisted()
    {
        BlacklistedWordFilter filter = new BlacklistedWordFilter()
        String badWord = "FIRETRUCK"
        Tweet tweet = new TweetBuilder().buildTweet(text: badWord + "S RULE")

        filter.blacklistWord(badWord)

        assert filter.isBlacklisted(tweet)
    }
}
