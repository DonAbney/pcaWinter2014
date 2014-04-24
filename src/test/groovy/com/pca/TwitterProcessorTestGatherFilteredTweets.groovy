package com.pca

import com.pca.test.utils.TweetBuilder
import sun.dc.pr.PRError

class TwitterProcessorTestGatherFilteredTweets extends GroovyTestCase {

    void testReturnsFilteredTweetsObject() {
        TwitterProcessor processor = new TwitterProcessor()
        assert processor.gatherFilteredTweets() instanceof FilteredTweets
    }

    void testAFreshProcessorContainsNoTweets() {
        TwitterProcessor processor = new TwitterProcessor()
        FilteredTweets filteredTweets = processor.gatherFilteredTweets()
        int black = filteredTweets.blackListedTweets.size()
        int white = filteredTweets.whiteListedTweets.size()
        int gray = filteredTweets.grayListedTweets.size()
        assert 0 == black + white + gray
    }
}
