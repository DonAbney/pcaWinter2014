package com.pca


class TwitterClient {

    List tweets;

    def filterByHashTag(def hashTag) {

        tweets.findAll{tweet->tweet.hashTags.contains(hashTag)}
    }

    def List<List<Map>> getTweetsFilterByTweetText(String textFilter)
    {
        []
    }
}