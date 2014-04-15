package com.pca

class FilteredTweets
{
    List blackListedTweets = [];
    List whiteListedTweets = [];
    List grayListedTweets = [];

    public FilteredTweets(List blackList = [], List whiteList = [], List tweets = []){
        blackListedTweets = blackList
        whiteListedTweets = whiteList
        this.grayListedTweets = tweets
    }

    private void setBlackListedTweets(List tweets) {}
}
