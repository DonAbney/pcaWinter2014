package com.pca


class TwitterDisplayTest extends GroovyTestCase
{
    TwitterDisplay twitterDisplay = new TwitterDisplay();

    def tweet1 = new Tweet(id: 1, handle: "doancea",
            text: "This is how Yoda #YOLO's  #OOYL",
            hashtags: ["YOLO", "OOYL"]
    )

    public void testDisplayPublicTimelineReturnsADiv()
    {
        String timeline = twitterDisplay.buildPublicTimelineHtml()
        assertTrue(timeline.startsWith("<div>"))
        assertTrue(timeline.endsWith("</div>"))
    }

    public void testTweetToHtmlDivReturnsADiv() {
        def tweetDiv = twitterDisplay.tweetToHtmlDiv(tweet1)
        assertTrue(tweetDiv.startsWith("<div>"))
        assertTrue(tweetDiv.endsWith("</div>"))
    }

    public void testTweetToHtmlContainsTweetText() {
        assertTrue(twitterDisplay.tweetToHtmlDiv(tweet1).contains(tweet1.text))
    }

    public void testTweetToHtmlContainsHandle() {
        assertTrue(twitterDisplay.tweetToHtmlDiv(tweet1).contains(tweet1.handle))
    }

}
