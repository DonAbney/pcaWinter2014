package com.pca

class TwitterProcessor {

    private Set<String> blacklistedHandles = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER)
    private Set<String> blacklistedWords   = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER)
    private Set<String> whitelistedHandles = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER)
    private WhitelistedHandleFilter whiteListedHandleFilter;
    FilteredTweets filteredTweets = new FilteredTweets()

    TwitterProcessor(WhitelistedHandleFilter whitelistedHandleFilter ) {
        this.whiteListedHandleFilter = whitelistedHandleFilter
    }
    void processTweets(Tweet tweet) {
        if(whiteListedHandleFilter.isWhitelisted(tweet)) {
            filteredTweets.whiteListedTweets.add(tweet)
        }
    }

    void blacklistHandle(String handle) {
        if(handle) {
            blacklistedHandles.add(handle);
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
