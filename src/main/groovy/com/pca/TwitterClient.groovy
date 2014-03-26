package com.pca


class TwitterClient {

    TwitterWrapper twitterWrapper

    List<Tweet> getTweets() {
        twitterWrapper.getTweets()
    }

    List<Tweet> getTweetsFilterByTweetText(String textFilter) {
        def allTweets = twitterWrapper.getTweets()

        !textFilter ? allTweets : allTweets.findAll{
            tweet->tweet.text.contains( textFilter )};
    }

    List<Tweet> getTweetsContainingHashTags(List hashTag) {
        def allTweets = twitterWrapper.getTweets()

        !hashTag ? allTweets : allTweets.findAll{
            it.hashtags.findAll{ hashTag.contains(it) } }
    }

}
