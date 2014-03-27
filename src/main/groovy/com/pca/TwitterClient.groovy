package com.pca


class TwitterClient {

    TwitterWrapper twitterWrapper
    WhiteList whiteList
    BlackList blackList

    List<Tweet> getTweets() {
        twitterWrapper.getTweets()
    }

    List<Tweet> getTweetsFilterByTweetText(String textFilter) {
        def allTweets = twitterWrapper.getTweets()

        !textFilter ? allTweets : allTweets.findAll{
            tweet->tweet.text.contains( textFilter )};
    }

    List<Tweet> getTweetsContainingHashTags(String hashTag) {
        def allTweets = twitterWrapper.getTweets()

        !hashTag ? allTweets : allTweets.findAll{
            it.hashtags.findAll{ hashTag.equals('#' + it) } }

    }

    def List getTweetsForDisplay()
    {
        twitterWrapper.getTweets().findAll {tweet ->
            !blackList.isBlackListed(tweet) || whiteList.isHandleInList(tweet.handle)
        }
    }

}
