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
        assert 0 == filteredTweets.blackListedTweets.size()
        assert 0 == filteredTweets.whiteListedTweets.size()
        assert 0 == filteredTweets.grayListedTweets.size()
    }
}
