package com.pca

/**
 * Created by andrew on 3/25/14.
 */
class BlackList
{
    def isBlackListed(Tweet tweet)
    {
        tweet.text.contains("blacklist")
    }
}
