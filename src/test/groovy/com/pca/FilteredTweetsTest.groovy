package com.pca

class FilteredTweetsTest extends GroovyTestCase{

    public void testFilteredTweetsAcceptsAListOfBlackListedTweets() {
        Tweet tweet1 = new Tweet(handle: 'Monkey', id: '1')
        List tweets = []
        tweets << tweet1
        FilteredTweets filteredTweets = new FilteredTweets(tweets)

        assert filteredTweets.blackListedTweets.contains(tweet1)
    }

    public void testFilteredTweetsAllowsAddingToBlacklist(){
        Tweet tweet1 = new Tweet(handle: 'Monkey', id: '1')
        List tweets = []
        tweets << tweet1
        FilteredTweets filteredTweets = new FilteredTweets(tweets)

        assert filteredTweets.getBlackListedTweets().contains(tweet1)
    }

    public void testFilteredTweetsAcceptsAListOfWhiteListedTweets() {
        Tweet tweet1 = new Tweet(handle: 'Monkey', id: '1')
        List blacklistedTweets = []
        List whitelistedTweets = []
        whitelistedTweets << tweet1
        FilteredTweets filteredTweets = new FilteredTweets(blacklistedTweets, whitelistedTweets)

        assert filteredTweets.whiteListedTweets.contains(tweet1)
    }

    public void testFilteredTweetsAllowsAddingToWhitelist(){
        Tweet tweet1 = new Tweet(handle: 'Monkey', id: '1')
        List blacklistedTweets = []
        List whitelistedTweets = [tweet1]
        FilteredTweets filteredTweets = new FilteredTweets(blacklistedTweets, whitelistedTweets)

        assert filteredTweets.getWhiteListedTweets().contains(tweet1)
    }
}