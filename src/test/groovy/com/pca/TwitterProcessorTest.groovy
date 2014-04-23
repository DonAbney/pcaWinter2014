package com.pca

import com.pca.test.utils.TweetBuilder

class TwitterProcessorTest extends GroovyTestCase {

    void testTwitterProcessorProcessTweetsPassesWhitelistFilterCorrectly() {
        TweetBuilder builder = new TweetBuilder()
        Tweet tweet = builder.buildTweet()
        WhitelistedHandleFilter whitelistedHandleFilter = new WhitelistedHandleFilter()
        whitelistedHandleFilter.metaClass.isWhitelisted() { return true}

        TwitterProcessor processor = new TwitterProcessor(whitelistedHandleFilter)
        processor.processTweets(tweet)

        assert processor.filteredTweets.whiteListedTweets.size() == 1

 /*
        FilteredTweets filteredTweets = new FilteredTweets();

        WhitelistedHandleFilter whitelistedHandleFilter = new WhitelistedHandleFilter()
        whitelistedHandleFilter.metaClass.isWhiteListed() { return true}
  */

    }
}
