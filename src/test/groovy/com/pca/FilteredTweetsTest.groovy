package com.pca

class FilteredTweetsTest extends GroovyTestCase{

    public void testFilteredTweetsAcceptsAListOfBlackListedTweets() {
        Tweet tweet1 = new Tweet(handle: 'Monkey', id: '1')
        List tweets = [tweet1]
        FilteredTweets filteredTweets = new FilteredTweets(tweets)

        assert filteredTweets.blackListedTweets.size() == 1
        assert filteredTweets.blackListedTweets.contains(tweet1)
    }

    public void testFilteredTweetsAcceptsAListOfWhiteListedTweets() {
        Tweet tweet1 = new Tweet(handle: 'Monkey', id: '1')
        List tweets = [tweet1]
        FilteredTweets filteredTweets = new FilteredTweets([], tweets)

        assert filteredTweets.whiteListedTweets.size() == 1
        assert filteredTweets.whiteListedTweets.contains(tweet1)
    }

    public void testFilteredTweetsAcceptsAListOfGrayListedTweets() {
        Tweet tweet1 = new Tweet(handle: 'Monkey', id: '1')
        List tweets = [tweet1]
        FilteredTweets filteredTweets = new FilteredTweets([], [], tweets)

        assert filteredTweets.grayListedTweets.size() == 1
        assert filteredTweets.grayListedTweets.contains(tweet1)
    }

    public void testFilteredTweetsDoesNotAllowBlacklistToBeOverwritten(){
        Tweet tweet1 = new Tweet(handle: 'Monkey', id: '1')
        Tweet tweet2 = new Tweet(handle: 'Ninja', id: '2')
        Tweet tweet3 = new Tweet(handle: 'Banana', id: '3')
        List expectedTweets = [tweet1, tweet2]
        List unexpectedTweets = [tweet3]
        FilteredTweets filteredTweets = new FilteredTweets(expectedTweets)

        filteredTweets.blackListedTweets = unexpectedTweets
        assert filteredTweets.blackListedTweets == expectedTweets
    }

    public void testFilteredTweetsDoesNotAllowWhitelistToBeOverwritten(){
        Tweet tweet1 = new Tweet(handle: 'Monkey', id: '1')
        Tweet tweet2 = new Tweet(handle: 'Ninja', id: '2')
        Tweet tweet3 = new Tweet(handle: 'Banana', id: '3')
        List expectedTweets = [tweet1, tweet2]
        List unexpectedTweets = [tweet3]
        FilteredTweets filteredTweets = new FilteredTweets([], expectedTweets)

        filteredTweets.whiteListedTweets = unexpectedTweets
        assert filteredTweets.whiteListedTweets == expectedTweets
    }

    public void testFilteredTweetsDoesNotAllowGraylistToBeOverwritten(){
        Tweet tweet1 = new Tweet(handle: 'Monkey', id: '1')
        Tweet tweet2 = new Tweet(handle: 'Ninja', id: '2')
        Tweet tweet3 = new Tweet(handle: 'Banana', id: '3')
        List expectedTweets = [tweet1, tweet2]
        List unexpectedTweets = [tweet3]
        FilteredTweets filteredTweets = new FilteredTweets([], [], expectedTweets)

        filteredTweets.grayListedTweets = unexpectedTweets
        assert filteredTweets.grayListedTweets == expectedTweets
    }
}