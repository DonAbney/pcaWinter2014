package com.pca

class FilteredTweets
{
    List blackListedTweets = [];
    List whiteListedTweets = [];

    public FilteredTweets(List blackList = [], List whiteList = []){
        blackListedTweets = blackList
        whiteListedTweets = whiteList
    }

    private void setBlackListedTweets(List tweets) {}
}
