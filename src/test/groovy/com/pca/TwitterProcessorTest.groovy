package com.pca

import com.pca.test.utils.TweetBuilder

class TwitterProcessorTest extends GroovyTestCase {

    void testTwitterProcessorProcessTweetsPassesWhitelistFilterCorrectly() {
        TweetBuilder builder = new TweetBuilder()
        Tweet tweet = builder.buildTweet()

        List<Tweet> tweetList = [tweet]

        WhitelistedHandleFilter whitelistedHandleFilter = new WhitelistedHandleFilter()
        whitelistedHandleFilter.metaClass.isWhitelisted() { return true}

        TwitterProcessor processor = new TwitterProcessor(whitelistedHandleFilter, new BlacklistedHandleFilter())
        processor.processTweets(tweetList)

        assert processor.filteredTweets.whiteListedTweets.size() == 1
    }

    void testTwitterProcessorProcessTweetsFailsWhitelistFilterCorrectly() {
        TweetBuilder builder = new TweetBuilder()
        Tweet tweet = builder.buildTweet()

        List<Tweet> tweetList = [tweet]

        WhitelistedHandleFilter whitelistedHandleFilter = new WhitelistedHandleFilter()
        whitelistedHandleFilter.metaClass.isWhitelisted() { return false}

        TwitterProcessor processor = new TwitterProcessor(whitelistedHandleFilter, new BlacklistedHandleFilter())
        processor.processTweets(tweetList)

        assert processor.filteredTweets.whiteListedTweets.size() == 0
    }

    void testTwitterProcessorProcessTweetsMatchesBlacklistHandleFilterCorrectly() {
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

        assert processor.filteredTweets.blackListedTweets.size() == 1
    }

    void testTwitterProcessorProcessTweetsDoNotMatchBlacklistHandleFilterCorrectly() {
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

        assert processor.filteredTweets.blackListedTweets.size() == 0
    }

    void testTwitterProcessorProcessTweetsMatchesBlacklistWordFilterCorrectly() {
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

        assert processor.filteredTweets.blackListedTweets.size() == 1
    }

    void testTwitterProcessorProcessTweetsDoesNotMatchBlacklistWordFilterCorrectly() {
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

        assert processor.filteredTweets.grayListedTweets.size() == 1
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

        assert processor.filteredTweets.whiteListedTweets.size() == 1
        assert processor.filteredTweets.blackListedTweets.size() == 2
        assert processor.filteredTweets.grayListedTweets.size() == 1

    }
}
