package com.pca

class FilteredTweetsTest extends GroovyTestCase{

    public void testFilteredTweetsAcceptsAListOfBlackListedTweets() {
        Tweet tweet1 = new Tweet(handle: 'Monkey', id: '1')
        List tweets = []
        tweets << tweet1
        FilteredTweets filteredTweets = new FilteredTweets(tweets)

        assert filteredTweets.blackListedTweets.contains(tweet1)
    }

    public void testFilteredTweetsDoesntAllowAddingToBlacklist(){
        Tweet tweet1 = new Tweet(handle: 'Monkey', id: '1')
        List tweets = []
        tweets << tweet1
        FilteredTweets filteredTweets = new FilteredTweets(tweets)

        assert filteredTweets.getBlackListedTweets().contains(tweet1)
    }
}