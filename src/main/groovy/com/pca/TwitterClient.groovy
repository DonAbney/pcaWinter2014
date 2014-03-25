package com.pca


class TwitterClient {

    TwitterWrapper twitterWrapper

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


}
