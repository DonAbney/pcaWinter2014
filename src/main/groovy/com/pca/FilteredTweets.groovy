package com.pca

class FilteredTweets
{
    List blackListedTweets = [];
    List whiteListedTweets = [];
    List grayListedTweets = [];

    public FilteredTweets(List blackListedTweets = [], List whiteListedTweets = [], List grayListedTweets = []){
        this.blackListedTweets = blackListedTweets
        this.whiteListedTweets = whiteListedTweets
        this.grayListedTweets = grayListedTweets
    }

    private void setBlackListedTweets(List tweets) {}
    private void setWhiteListedTweets(List tweets) {}
    private void setGrayListedTweets(List tweets) {}
}
