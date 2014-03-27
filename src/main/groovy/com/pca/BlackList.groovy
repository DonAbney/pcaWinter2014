package com.pca

/**
 * Created by andrew on 3/25/14.
 */
class BlackList {

    Set handles

    boolean isBlackListed(Tweet tweet) {
        isBlackListedUser(tweet)
    }

    private boolean isBlackListedUser(Tweet tweet) {
        tweet.text.contains('blacklist') || handles.find {
            tweet.handle.equalsIgnoreCase(it)
        }
    }
}
