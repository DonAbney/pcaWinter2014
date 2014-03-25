package com.pca


class Tweet {

    long id
    String handle
    String text
    List hashtags

    def setHashtags(String hashtag) {
        this.hashtags = [hashtag]
    }

}
