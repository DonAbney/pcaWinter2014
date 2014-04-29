package com.pca

import com.pca.test.utils.TweetBuilder

class TwitterProcessorGreylistTest extends GroovyTestCase {

    void testBlacklistingAGreylistedTweetMovesItFromGreylistToBlacklist() {
        TwitterProcessor processor = new TwitterProcessor()
        TweetBuilder builder = new TweetBuilder()

        Tweet tweet = builder.buildTweet()

        processor.greylistedTweets.add(tweet)

        processor.blacklistTweet(tweet)

        assert processor.blacklistedTweets.contains(tweet)
        assert !processor.greylistedTweets.contains(tweet)
    }

    void testTryingToBlacklistATweetNotInGreylistDoesNothing() {
        TwitterProcessor processor = new TwitterProcessor()
        TweetBuilder builder = new TweetBuilder()

        Tweet tweet = builder.buildTweet()

        processor.blacklistTweet(tweet)

        assert !processor.blacklistedTweets.contains(tweet)
    }
}
