package com.pca


class TwitterDisplayTest extends GroovyTestCase
{
    TwitterDisplay twitterDisplay = new TwitterDisplay();

    def tweet1 = new Tweet(id: 1, handle: "doancea",
            text: "This is how Yoda #YOLO's  #OOYL",
            hashtags: ["YOLO", "OOYL"]
    )

    def tweet2 = new Tweet(id: 2, handle: "jgarrett",
            text: "Here be another tweet matey #talklikeapirate",
            hashtags: ["talklikeapirate"]
    )

    List tweets = [tweet1, tweet2]

    TwitterWrapper wrapper
    TwitterClient twitterClient
    List publicTimeline

    void setUp()
    {
        wrapper = new TwitterWrapper()
        twitterClient = new TwitterClient(twitterWrapper: wrapper)
        publicTimeline = (new TwitterClient(twitterWrapper: wrapper)).getTweets()
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

    public void testTweetsListToHtmlContainsTweetsData() {
        def tweetsHtml = twitterDisplay.tweetListToHtmlDivs(tweets)
        assertTrue(tweets.every { tweetsHtml.contains(it.text); tweetsHtml.contains(it.handle); })
    }

    public void testBuildPublicTimelineHtmlReturnsHtmlDoc() {
        def tweetDiv = twitterDisplay.buildPublicTimelineHtml(twitterClient)
        assertTrue(tweetDiv.startsWith("<html>"))
        assertTrue(tweetDiv.endsWith("</html>"))
    }

    public void testBuildPublicTimelineHtmlContainsTweetsData() {
        def tweetsHtml = twitterDisplay.buildPublicTimelineHtml(twitterClient)
        assertTrue(publicTimeline.every { tweetsHtml.contains(it.text); tweetsHtml.contains(it.handle) })
    }

}
