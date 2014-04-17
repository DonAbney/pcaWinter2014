package com.pca.test.utils

import com.pca.Tweet

class TweetBuilder {

    static int id = 0;

    public Tweet buildTweet(Map params = [:]) {
        Map updatedParams = [
            id: params.id ?: id++,
            handle: params.handle ?: "userHandle${id}",
            text: params.text ?: "default text",
            hashtags: params.hashtags ?: []
        ]

        new Tweet(updatedParams)
    }
}
