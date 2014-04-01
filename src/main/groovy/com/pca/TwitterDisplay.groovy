package com.pca
import groovy.xml.MarkupBuilder


class TwitterDisplay {

    StringWriter writer = new StringWriter()

    public String buildPublicTimelineHtml(TwitterClient twitterClient) {
        def build = new MarkupBuilder(writer);
        build.html{
            head()
            body(
                this.tweetListToHtmlDivs(twitterClient.getTweets())
            )
        }
        writer.toString()
    }

    public String tweetToHtmlDiv(Tweet tweet) {
        def build = new MarkupBuilder(writer)

        build.div {
                p (tweet.text)
                p (tweet.handle)
            }

        writer.toString()
    }

    public String tweetListToHtmlDivs(List tweets)
    {
        String result
        tweets.each { result += tweetToHtmlDiv(it) }
        result
    }
}
