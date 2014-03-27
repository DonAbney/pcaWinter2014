package com.pca

class TwitterClientTest extends GroovyTestCase {

    public void test_getTweets_ReturnsAllTweets() {
        def tweets = [new Tweet(id: 1, handle: 'jason', text: 'hey everyone'),
                new Tweet(id: 2, handle: 'jason', text: 'yo'),
                new Tweet(id: 3, handle: 'sleepy', text: 'look, I got a #hashtag', hashtags: ['hashtag'])]

        TwitterWrapper wrapper = getOverwrittenTwitterWrapper(tweets)
        TwitterClient client = new TwitterClient(twitterWrapper: wrapper)
        assertEquals(tweets, client.getTweets())
    }

    public void test_getTweetsFilterByTweetText_returnsAllTweetsWhenFilterIsEmptyString() {
        def tweets = [new Tweet(id: 1, handle: 'aUserName', text: 'no hash tags yo!!'),
                new Tweet(id: 2, handle: 'anotherUser', text: 'a #silly tweet', hashtags: ['silly']),
                new Tweet(id: 3, handle: 'aUserName', text: 'a boring tweet')]

        TwitterWrapper wrapper = getOverwrittenTwitterWrapper(tweets)
        TwitterClient client = new TwitterClient(twitterWrapper: wrapper);
        assertEquals(tweets, client.getTweetsFilterByTweetText(''));
    }

    public void test_getTweetsFilterByTweetText_ReturnsAllTweetsWithNullFilter() {
        def tweets = [new Tweet(id: 1, handle: 'aUserName', text: 'no hash tags yo!!'),
                new Tweet(id: 2, handle: 'anotherUser', text: 'a #silly tweet', hashtags: ['silly']),
                new Tweet(id: 3, handle: 'aUserName', text: 'a boring tweet')]

        TwitterWrapper wrapper = getOverwrittenTwitterWrapper(tweets)
        TwitterClient client = new TwitterClient(twitterWrapper: wrapper);
        assertEquals(tweets, client.getTweetsFilterByTweetText())
    }

    public void test_getTweetsFilterByTweetText_ReturnsCorrectTweets() {
        def tweets = [new Tweet(id: 1, handle: 'aUserName', text: 'no hash tags yo!!'),
                new Tweet(id: 2, handle: 'anotherUser', text: 'a #silly tweet', hashtags: ['silly']),
                new Tweet(id: 3, handle: 'aUserName', text: 'a boring tweet')]

        TwitterWrapper wrapper = getOverwrittenTwitterWrapper(tweets)
        TwitterClient client = new TwitterClient(twitterWrapper: wrapper);
        def retrievedTweets = client.getTweetsFilterByTweetText('tweet');
        assertTrue(retrievedTweets.size() == 2);
        assertEquals(tweets[1], retrievedTweets[0])
        assertEquals(tweets[2], retrievedTweets[1])
    }

    public void test_getTweetsContainingHashTags_ReturnsTweetsContainingPassedInHashTag() {
        def tweets = [new Tweet (id: 1, handle: 'someHandle', text: 'tweet 1 #include #monkey', hashtags: ['monkey', 'include'] ),
                new Tweet (id: 2, handle: 'anotherHandle', text: 'tweet 2' ),
                new Tweet (id: 3, handle: 'someHandle', text: 'another tweet #include', hashtags: ['include'] ),
                new Tweet (id: 4, handle: 'lastHandle', text: 'tweet without include hashtag' ),
                new Tweet (id: 4, handle: 'lastHandle', text: 'tweet with #in part of previous tweet', hashtags: ['in'] )]

        TwitterWrapper wrapper = getOverwrittenTwitterWrapper(tweets)
        TwitterClient client = new TwitterClient(twitterWrapper: wrapper)
        assertEquals([tweets[0], tweets[2]], client.getTweetsContainingHashTags('#include'))
    }

    public void test_getTweetsContainingHashTags_ReturnsAllTweetsWithNoFilter() {
        def tweets = [new Tweet (id: 1, handle: 'someHandle', text: 'tweet 1 #include #monkey', hashtags: ['monkey', 'include'] ),
                new Tweet (id: 2, handle: 'anotherHandle', text: 'tweet 2' ),
                new Tweet (id: 3, handle: 'someHandle', text: 'another tweet #include', hashtags: ['include'] ),
                new Tweet (id: 4, handle: 'lastHandle', text: 'tweet without include hashtag' )]

        TwitterWrapper wrapper = getOverwrittenTwitterWrapper(tweets)
        TwitterClient client = new TwitterClient(twitterWrapper: wrapper)
        assertEquals(tweets, client.getTweetsContainingHashTags())
    }

    public void test_getTweetsContainingHashTags_ReturnsNoTweetsPassingInUnusedHashTag() {
        def tweets = [new Tweet (id: 1, handle: 'someHandle', text: 'tweet 1 #include #monkey', hashtags: ['monkey', 'include'] ),
                new Tweet (id: 2, handle: 'anotherHandle', text: 'tweet 2' ),
                new Tweet (id: 3, handle: 'someHandle', text: 'another tweet #include', hashtags: ['include'] ),
                new Tweet (id: 4, handle: 'lastHandle', text: 'tweet without include hashtag' )]

        TwitterWrapper wrapper = getOverwrittenTwitterWrapper(tweets)
        TwitterClient client = new TwitterClient(twitterWrapper: wrapper)
        assertEquals([], client.getTweetsContainingHashTags('#unused'))
    }

    private TwitterWrapper getOverwrittenTwitterWrapper(List tweets) {
        new TwitterWrapper() {
            @Override
            List getTweets() {
                return tweets
            }
        }
    }
}
