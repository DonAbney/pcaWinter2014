package com.pca

/**
 * Created by andrew on 3/25/14.
 */
class BlackList {

    Set handles
    Set words = ['blacklist']

    boolean isBlackListed(Tweet tweet) {
        isBlackListedUser(tweet)
    }

    private boolean isBlackListedUser(Tweet tweet) {
        words.find{tweet.text.contains(it)}  || handles.find {
            tweet.handle.equalsIgnoreCase(it)
        }
    }
}
