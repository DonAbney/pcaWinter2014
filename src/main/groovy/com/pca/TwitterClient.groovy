package com.pca


class TwitterClient {

    TwitterWrapper twitterWrapper

    List getTweets(def hashTag) {
        List allTweets = twitterWrapper.getTweets()
        isHashTag(hashTag) ? allTweets.findAll { tweet -> tweet.tweet.contains(hashTag) } : allTweets
    }

    private def isHashTag(hashTag) {
        hashTag?.startsWith('#')
    }


}
