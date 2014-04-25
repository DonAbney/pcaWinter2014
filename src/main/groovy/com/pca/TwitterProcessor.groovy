package com.pca

class TwitterProcessor {

    private Set<String> blacklistedHandles = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER)
    private Set<String> blacklistedWords   = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER)
    private Set<String> whitelistedHandles = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER)
    private BlacklistedHandleFilter blacklistedHandleFilter
    private BlacklistedWordFilter blacklistedWordFilter
    private WhitelistedHandleFilter whitelistedHandleFilter
    FilteredTweets filteredTweets = new FilteredTweets()

    TwitterProcessor(WhitelistedHandleFilter whitelistedHandleFilter = null, BlacklistedHandleFilter blacklistedHandleFilter = null, BlacklistedWordFilter blacklistedWordFilter = null) {
        this.whitelistedHandleFilter = whitelistedHandleFilter
        this.blacklistedHandleFilter = blacklistedHandleFilter
        this.blacklistedWordFilter   = blacklistedWordFilter

    }
    void processTweets(List<Tweet> tweets) {
        tweets.every( { tweet ->
            if(whitelistedHandleFilter.isWhitelisted(tweet.getHandle())) {
                filteredTweets.whiteListedTweets.add(tweet)
            }
            else if(blacklistedHandleFilter.isBlacklisted(tweet.getHandle()) || blacklistedWordFilter.isBlacklisted(tweet.getText())) {
                filteredTweets.blackListedTweets.add(tweet)
            } else {
                filteredTweets.grayListedTweets.add(tweet)
            }
        })
    }

    void blacklistHandle(String handle) {
        if(handle) {
            blacklistedHandles.add(handle)
            unwhitelistHandle(handle)
        }
    }
    
    void blacklistWord(String word) {
        if(word) blacklistedWords.add(word)
    }
    
    void whitelistHandle(String handle) {
        if (handle) {
            whitelistedHandles.add(handle)
            unblacklistHandle(handle)
        }
    }
    
    void unblacklistHandle(String handle) {
        if (handle) {
            blacklistedHandles.remove(handle)
        }
    }

    void unblacklistWord(String word) {}

    void unwhitelistHandle(String handle) {
        if (handle) {
            whitelistedHandles.remove(handle)
        }
    }

    boolean isWordBlacklisted(String word) {
        return word ? blacklistedWords.contains(word) : false
    }

    private TreeSet<String> getBlackListedWords() {}

    private void setBlacklistedHandles(Set<String> blacklistedHandles) {}
    private void setBlacklistedWords(Set<String> blacklistedWords) {}
    private void setWhitelistedHandles(Set<String> whitelistedHandles) {}
}
