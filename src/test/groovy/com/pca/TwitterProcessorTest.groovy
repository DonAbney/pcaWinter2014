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
        Tweet tweet1 = builder.buildTweet([id: 1])
        Tweet tweet2 = builder.buildTweet([id: 2])
        Tweet tweet3 = builder.buildTweet([id: 3])
        Tweet tweet4 = builder.buildTweet([id: 4])

        List<Tweet> tweetList = [tweet1, tweet2, tweet3, tweet4]

        println tweet1.id 

        WhitelistedHandleFilter whitelistedHandleFilter = new WhitelistedHandleFilter()
        whitelistedHandleFilter.metaClass.isWhitelisted(Tweet) { tweet -> if(tweet.id % 4 == 0) { return true }; return false }
        BlacklistedHandleFilter blacklistedHandleFilter = new BlacklistedHandleFilter()
        blacklistedHandleFilter.metaClass.isBlacklisted(Tweet) { tweet -> if(tweet.id % 3 == 0) { return true }; return false }
        BlacklistedWordFilter blacklistedWordFilter = new BlacklistedWordFilter()
        blacklistedWordFilter.metaClass.isBlacklisted(Tweet) { tweet -> if(tweet.id % 2 == 0) { return true }; return false }

        TwitterProcessor processor = new TwitterProcessor(whitelistedHandleFilter, blacklistedHandleFilter, blacklistedWordFilter)
        processor.processTweets(tweetList)

        assert processor.filteredTweets.whiteListedTweets.size() == 1
        assert processor.filteredTweets.blackListedTweets.size() == 2
        assert processor.filteredTweets.grayListedTweets.size() == 1

    }
}
