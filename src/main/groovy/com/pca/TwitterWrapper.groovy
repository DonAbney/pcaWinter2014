package com.pca

class TwitterWrapper {

    List<Tweet> getTweets() {
        [
            new Tweet(handle: 'aUserName', text: 'a tweet with a tag #fun', hashtags: ['fun']),
            new Tweet(handle: 'anotherUser', text: 'a #silly tweet', hashtags: ['silly']),
            new Tweet(handle: 'aUserName', text: 'a boring tweet')
        ]

    }
}
