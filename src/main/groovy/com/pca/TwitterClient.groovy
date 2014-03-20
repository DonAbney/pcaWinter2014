package com.pca


class TwitterClient {

    def twitterWrapper
    List tweets

    def retrieveTweets(def hashTag) {
        this.tweets = twitterWrapper.getTweets()
        filterByHashTag(hashTag)
    }

    private def filterByHashTag(def hashTag) {
        isHashTag(hashTag) ? tweets.findAll { tweet -> tweet.tweet.contains(hashTag) } : tweets
    }

    private def isHashTag(hashTag) {
        hashTag?.startsWith('#')
    }


}
