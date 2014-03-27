package com.pca

class BlackList
{
    def isBlackListed(Tweet tweet)
    {
        tweet.text.contains("blacklist")
    }
}
