package com.pca

class FilteredTweets
{
    List blackListedTweets = [];
    List whiteListedTweets = [];
    List tweets = [];

    public FilteredTweets(List blackList = [], List whiteList = [], List tweets = []){
        blackListedTweets = blackList
        whiteListedTweets = whiteList
        this.tweets = tweets
    }

    private void setBlackListedTweets(List tweets) {}
}
