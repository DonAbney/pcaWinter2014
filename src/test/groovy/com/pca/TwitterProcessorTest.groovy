package com.pca

import com.pca.test.utils.TweetBuilder

class TwitterProcessorTest extends GroovyTestCase {

    void testTwitterProcessorFiltersFalseFalseFalse() {
        TweetBuilder builder = new TweetBuilder()
        Tweet tweet = builder.buildTweet()

        List<Tweet> tweetList = [tweet]

        WhitelistedHandleFilter whitelistedHandleFilter = new WhitelistedHandleFilter()
        whitelistedHandleFilter.metaClass.isWhitelisted() { return false }
        BlacklistedHandleFilter blacklistedHandleFilter = new BlacklistedHandleFilter()
        blacklistedHandleFilter.metaClass.isBlacklisted() { return false }
        BlacklistedWordFilter blacklistedWordFilter = new BlacklistedWordFilter()
        blacklistedWordFilter.metaClass.isBlacklisted() { return false }

        TwitterProcessor processor = new TwitterProcessor(whitelistedHandleFilter, blacklistedHandleFilter, blacklistedWordFilter)
        processor.processTweets(tweetList)

        assertFalse processor.filteredTweets.whiteListedTweets.contains(tweet)
        assertFalse processor.filteredTweets.blackListedTweets.contains(tweet)
        assert processor.filteredTweets.grayListedTweets.contains(tweet)

    }

    void testTwitterProcessorFiltersFalseFalseTrue() {
        TweetBuilder builder = new TweetBuilder()
        Tweet tweet = builder.buildTweet()

        List<Tweet> tweetList = [tweet]

        WhitelistedHandleFilter whitelistedHandleFilter = new WhitelistedHandleFilter()
        whitelistedHandleFilter.metaClass.isWhitelisted() { return false }
        BlacklistedHandleFilter blacklistedHandleFilter = new BlacklistedHandleFilter()
        blacklistedHandleFilter.metaClass.isBlacklisted() { return false }
        BlacklistedWordFilter blacklistedWordFilter = new BlacklistedWordFilter()
        blacklistedWordFilter.metaClass.isBlacklisted() { return true }

        TwitterProcessor processor = new TwitterProcessor(whitelistedHandleFilter, blacklistedHandleFilter, blacklistedWordFilter)
        processor.processTweets(tweetList)

        assertFalse processor.filteredTweets.whiteListedTweets.contains(tweet)
        assert processor.filteredTweets.blackListedTweets.contains(tweet)
        assertFalse processor.filteredTweets.grayListedTweets.contains(tweet)
    }

    void testTwitterProcessorFiltersFalseTrueFalse() {
        TweetBuilder builder = new TweetBuilder()
        Tweet tweet = builder.buildTweet()

        List<Tweet> tweetList = [tweet]

        WhitelistedHandleFilter whitelistedHandleFilter = new WhitelistedHandleFilter()
        whitelistedHandleFilter.metaClass.isWhitelisted() { return false }
        BlacklistedHandleFilter blacklistedHandleFilter = new BlacklistedHandleFilter()
        blacklistedHandleFilter.metaClass.isBlacklisted() { return true }
        BlacklistedWordFilter blacklistedWordFilter = new BlacklistedWordFilter()
        blacklistedWordFilter.metaClass.isBlacklisted() { return false }

        TwitterProcessor processor = new TwitterProcessor(whitelistedHandleFilter, blacklistedHandleFilter, blacklistedWordFilter)
        processor.processTweets(tweetList)

        assertFalse processor.filteredTweets.whiteListedTweets.contains(tweet)
        assert processor.filteredTweets.blackListedTweets.contains(tweet)
        assertFalse processor.filteredTweets.grayListedTweets.contains(tweet)
    }

    void testTwitterProcessorFiltersFalseTrueTrue() {
        TweetBuilder builder = new TweetBuilder()
        Tweet tweet = builder.buildTweet()

        List<Tweet> tweetList = [tweet]

        WhitelistedHandleFilter whitelistedHandleFilter = new WhitelistedHandleFilter()
        whitelistedHandleFilter.metaClass.isWhitelisted() { return false }
        BlacklistedHandleFilter blacklistedHandleFilter = new BlacklistedHandleFilter()
        blacklistedHandleFilter.metaClass.isBlacklisted() { return true }
        BlacklistedWordFilter blacklistedWordFilter = new BlacklistedWordFilter()
        blacklistedWordFilter.metaClass.isBlacklisted() { return true }

        TwitterProcessor processor = new TwitterProcessor(whitelistedHandleFilter, blacklistedHandleFilter, blacklistedWordFilter)
        processor.processTweets(tweetList)

        assertFalse processor.filteredTweets.whiteListedTweets.contains(tweet)
        assert processor.filteredTweets.blackListedTweets.contains(tweet)
        assertFalse processor.filteredTweets.grayListedTweets.contains(tweet)
    }

    void testTwitterProcessorFiltersTrueFalseFalse() {
        TweetBuilder builder = new TweetBuilder()
        Tweet tweet = builder.buildTweet()

        List<Tweet> tweetList = [tweet]

        WhitelistedHandleFilter whitelistedHandleFilter = new WhitelistedHandleFilter()
        whitelistedHandleFilter.metaClass.isWhitelisted() { return true }
        BlacklistedHandleFilter blacklistedHandleFilter = new BlacklistedHandleFilter()
        blacklistedHandleFilter.metaClass.isBlacklisted() { return false }
        BlacklistedWordFilter blacklistedWordFilter = new BlacklistedWordFilter()
        blacklistedWordFilter.metaClass.isBlacklisted() { return false }

        TwitterProcessor processor = new TwitterProcessor(whitelistedHandleFilter, blacklistedHandleFilter, blacklistedWordFilter)
        processor.processTweets(tweetList)

        assert processor.filteredTweets.whiteListedTweets.contains(tweet)
        assertFalse processor.filteredTweets.blackListedTweets.contains(tweet)
        assertFalse processor.filteredTweets.grayListedTweets.contains(tweet)
    }

    void testTwitterProcessorFiltersTrueFalseTrue() {
        TweetBuilder builder = new TweetBuilder()
        Tweet tweet = builder.buildTweet()

        List<Tweet> tweetList = [tweet]

        WhitelistedHandleFilter whitelistedHandleFilter = new WhitelistedHandleFilter()
        whitelistedHandleFilter.metaClass.isWhitelisted() { return true }
        BlacklistedHandleFilter blacklistedHandleFilter = new BlacklistedHandleFilter()
        blacklistedHandleFilter.metaClass.isBlacklisted() { return false }
        BlacklistedWordFilter blacklistedWordFilter = new BlacklistedWordFilter()
        blacklistedWordFilter.metaClass.isBlacklisted() { return true }

        TwitterProcessor processor = new TwitterProcessor(whitelistedHandleFilter, blacklistedHandleFilter, blacklistedWordFilter)
        processor.processTweets(tweetList)

        assert processor.filteredTweets.whiteListedTweets.contains(tweet)
        assertFalse processor.filteredTweets.blackListedTweets.contains(tweet)
        assertFalse processor.filteredTweets.grayListedTweets.contains(tweet)
    }

    void testTwitterProcessorFiltersTrueTrueFalse() {
        TweetBuilder builder = new TweetBuilder()
        Tweet tweet = builder.buildTweet()

        List<Tweet> tweetList = [tweet]

        WhitelistedHandleFilter whitelistedHandleFilter = new WhitelistedHandleFilter()
        whitelistedHandleFilter.metaClass.isWhitelisted() { return true }
        BlacklistedHandleFilter blacklistedHandleFilter = new BlacklistedHandleFilter()
        blacklistedHandleFilter.metaClass.isBlacklisted() { return true }
        BlacklistedWordFilter blacklistedWordFilter = new BlacklistedWordFilter()
        blacklistedWordFilter.metaClass.isBlacklisted() { return false }

        TwitterProcessor processor = new TwitterProcessor(whitelistedHandleFilter, blacklistedHandleFilter, blacklistedWordFilter)
        processor.processTweets(tweetList)

        assert processor.filteredTweets.whiteListedTweets.contains(tweet)
        assertFalse processor.filteredTweets.blackListedTweets.contains(tweet)
        assertFalse processor.filteredTweets.grayListedTweets.contains(tweet)
    }

    void testTwitterProcessorFiltersTrueTrueTrue() {
        TweetBuilder builder = new TweetBuilder()
        Tweet tweet = builder.buildTweet()

        List<Tweet> tweetList = [tweet]

        WhitelistedHandleFilter whitelistedHandleFilter = new WhitelistedHandleFilter()
        whitelistedHandleFilter.metaClass.isWhitelisted() { return true }
        BlacklistedHandleFilter blacklistedHandleFilter = new BlacklistedHandleFilter()
        blacklistedHandleFilter.metaClass.isBlacklisted() { return true }
        BlacklistedWordFilter blacklistedWordFilter = new BlacklistedWordFilter()
        blacklistedWordFilter.metaClass.isBlacklisted() { return true }

        TwitterProcessor processor = new TwitterProcessor(whitelistedHandleFilter, blacklistedHandleFilter, blacklistedWordFilter)
        processor.processTweets(tweetList)

        assert processor.filteredTweets.whiteListedTweets.contains(tweet)
        assertFalse processor.filteredTweets.blackListedTweets.contains(tweet)
        assertFalse processor.filteredTweets.grayListedTweets.contains(tweet)
    }

    void testTwitterProcessorMultipleTweetsMutiplePaths() {
        TweetBuilder builder = new TweetBuilder()
        Tweet tweet1 = builder.buildTweet([handle:"WLH-True"])  // match WhitelistHandleFilter
        Tweet tweet2 = builder.buildTweet([handle:"BLH-True"])  // match BlacklistHandleFilter
        Tweet tweet3 = builder.buildTweet([text: "BLW-True"])   // match BlacklistWordFilter
        Tweet tweet4 = builder.buildTweet()                     // don't match any filter

        List<Tweet> tweetList = [tweet1, tweet2, tweet3, tweet4]

        WhitelistedHandleFilter whitelistedHandleFilter = new WhitelistedHandleFilter()
        whitelistedHandleFilter.metaClass.isWhitelisted() { String handle -> if(handle.equals("WLH-True"))  return true;  else return false }
        BlacklistedHandleFilter blacklistedHandleFilter = new BlacklistedHandleFilter()
        blacklistedHandleFilter.metaClass.isBlacklisted() { String handle -> if(handle.equals("BLH-True"))  return true;  else return false }
        BlacklistedWordFilter blacklistedWordFilter = new BlacklistedWordFilter()
        blacklistedWordFilter.metaClass.isBlacklisted() { String word -> if(word.equals("BLW-True"))  return true ; else return false }

        TwitterProcessor processor = new TwitterProcessor(whitelistedHandleFilter, blacklistedHandleFilter, blacklistedWordFilter)
        processor.processTweets(tweetList)

        assert processor.filteredTweets.whiteListedTweets.contains(tweet1)
        assert processor.filteredTweets.blackListedTweets.contains(tweet2)
        assert processor.filteredTweets.blackListedTweets.contains(tweet3)
        assert processor.filteredTweets.grayListedTweets.contains(tweet4)

    }
}
