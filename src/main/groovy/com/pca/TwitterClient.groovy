package com.pca


class TwitterClient {

    TwitterWrapper twitterWrapper
    WhiteList whiteList
    BlackList blackList

    List getTweets(def hashTag) {
        List allTweets = twitterWrapper.getTweets()
        isHashTag(hashTag) ? allTweets.findAll { tweet -> tweet.tweet.contains(hashTag) } : allTweets
    }

    def List<List<Map>> getTweetsFilterByTweetText(String textFilter)
    {
        twitterWrapper.getTweets().findAll{tweet->tweet.tweet.contains(textFilter)};
    }

    private def isHashTag(hashTag) {
        hashTag?.startsWith('#')
    }

    def List getTweetsForDisplay()
    {
        twitterWrapper.getTweets().findAll {tweet ->
            !blackList.isBlackListed(tweet) || whiteList.isHandleInList(tweet.handle)
        }
    }


}
