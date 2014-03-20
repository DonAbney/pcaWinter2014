package com.pca


class TwitterClient {

    private TwitterWrapper wrapper
    private List tweets

    def List getTweets() {
        this.wrapper.getTweets()
    }

    def filterByHashTag(def hashTag) {

        tweets.findAll{tweet->tweet.hashTags.contains(hashTag)}
    }

    def List<List<Map>> getTweetsFilterByTweetText(String textFilter)
    {
        []
    }
}
