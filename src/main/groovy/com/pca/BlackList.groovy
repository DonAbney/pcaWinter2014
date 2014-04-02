package com.pca

class BlackList {

    Set handles
    Set words = ['blacklist']

    boolean isBlackListed(Tweet tweet) {
        isBlackListedUser(tweet)
    }

    private boolean isBlackListedUser(Tweet tweet) {
        tweet.text.contains('blacklist') || handles.find {
            tweet.handle.equalsIgnoreCase(it)
        }
    }

    public void addWord(String bad_word) {
        words.add(bad_word)
    }
}
