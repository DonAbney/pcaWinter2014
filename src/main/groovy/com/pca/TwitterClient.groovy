package com.pca


class TwitterClient {

    def twitterWrapper

    def List getTweets() {
        this.twitterWrapper.getTweets()
    }

    def retrieveTweets(def hashTag) {
        isHashTag(hashTag) ? twitterWrapper.getTweets().findAll { tweet -> tweet.tweet.contains(hashTag) } : tweets
    }

    private def isHashTag(hashTag) {
        hashTag?.startsWith('#')
    }


}
