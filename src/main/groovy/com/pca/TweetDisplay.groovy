package com.pca

class TweetDisplay {
	
	String renderTweetsToHTML(List tweets) {
		tweets.join('\\n')
	}
}
