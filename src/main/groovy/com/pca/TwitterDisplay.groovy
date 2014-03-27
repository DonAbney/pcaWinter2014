package com.pca
import groovy.xml.MarkupBuilder


class TwitterDisplay {

    StringWriter writer = new StringWriter()

    public String getPublicTimelineHtml() {
        "<div></div>"
    }

    protected String tweetToHtmlDiv(Tweet tweet) {
        def build = new MarkupBuilder(writer)

        build.div {
                p (tweet.text)
            }

        writer.toString()
    }
}
