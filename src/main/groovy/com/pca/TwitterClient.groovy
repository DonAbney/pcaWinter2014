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

        textFilter ? getTweetsContainingText(textFilter, allTweets) : allTweets
    }

    List<Tweet> getTweetsContainingHashTags(String hashTag) {
        def allTweets = twitterWrapper.getTweets()

        hashTag ? getTweetsContainingHashTag(hashTag, allTweets) : allTweets
    }

    def List getTweetsForDisplay()
    {
        twitterWrapper.getTweets().findAll {tweet ->
            !blackList.isBlackListed(tweet) || whiteList.isHandleInList(tweet.handle)
        }
    }

    def List getBlackListedTweets() {
        List returnList = []
        getTweets().each {
            if(blackList.isBlackListed(it)) {
                returnList.add(it)
            }
        }
        returnList
    }

    private def List getTweetsContainingText(String textFilter, List<Tweet> allTweets) {
        allTweets.findAll{tweet->tweet.text.contains( textFilter )}
    }

    private def List getTweetsContainingHashTag(String hashTag, List<Tweet> allTweets) {
        allTweets.findAll{ it.hashtags.findAll{ hashTag.equals('#' + it) } }
    }

}
