package com.pca

class TwitterWrapper {

    
    List getTweets() {
        [
            [hashtags:['#foo', '#bar'], user:'aUserName', tweet:'a tweet',],
            [hashtags:['#baz'], user:'anotherUser', tweet:'a longer tweet',],
            [hashtags:[], user:'aUserName', tweet:'tweet',],
        ]

    }
}
