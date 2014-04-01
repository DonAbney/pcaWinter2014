package com.pca


class TwitterClient {

    TwitterWrapper twitterWrapper
    WhiteList whiteList
    BlackList blackList

    public TwitterClient(BlackList blackList = new BlackList()) {
        this.blackList = blackList
    }

    List getTweets(def hashTag) {
        List allTweets = twitterWrapper.getTweets()
        List tweetsFilteredByHashTags = isHashTag(hashTag) ? allTweets.findAll { tweet -> tweet.text.contains(hashTag) } : allTweets
        List tweetsMinusBlackListedTweets = tweetsFilteredByHashTags.findAll {
            !blackList.isBlackListed(it)
        }

        tweetsMinusBlackListedTweets
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
