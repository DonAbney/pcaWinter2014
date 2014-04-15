package com.pca

class FilteredTweetsTest extends GroovyTestCase{

    public void testFilteredTweetsAllowsAccessToBlacklist(){
        Tweet tweet1 = new Tweet(handle: 'Monkey', id: '1')
        FilteredTweets filteredTweets = new FilteredTweets()

        filteredTweets.blackListedTweets = [tweet1]

        assert filteredTweets.blackListedTweets.size() == 1
        assert filteredTweets.blackListedTweets.contains(tweet1)
    }

    public void testFilteredTweetsAcceptsAListOfBlackListedTweets() {
        Tweet tweet1 = new Tweet(handle: 'Monkey', id: '1')
        FilteredTweets filteredTweets = new FilteredTweets(blackListedTweets: [tweet1])

        assert filteredTweets.blackListedTweets.contains(tweet1)
    }


}