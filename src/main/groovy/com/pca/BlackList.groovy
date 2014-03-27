package com.pca

class BlackList {

    Set handles

    boolean isBlackListed(Tweet tweet) {
        isBlackListedUser(tweet)
    }

    private boolean isBlackListedUser(Tweet tweet) {
        handles.find {
            tweet.handle.equalsIgnoreCase(it)
        }
    }
}
