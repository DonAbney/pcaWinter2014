package com.pca


class TwitterDisplayTest extends GroovyTestCase
{
    public void testDisplayPublicTimelineReturnsADiv()
    {
        TwitterDisplay twitterDisplay = new TwitterDisplay()
        String timeline = twitterDisplay.getPublicTimelineHtml()
        assertTrue(timeline.startsWith("<div>"))
        assertTrue(timeline.endsWith("</div>"))
    }
}
