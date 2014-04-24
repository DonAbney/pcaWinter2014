package com.pca

class TwitterProcessor {
    private TreeSet<String> blacklistedHandles = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER)
    private TreeSet<String> blacklistedWords = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER)
    private TreeSet<String> whitelistedHandles = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER)

    Set<Tweet> greylistedTweets = new HashSet<Tweet>()
    Set<Tweet> blacklistedTweets = new HashSet<Tweet>()

    void blacklistHandle(String handle) {
        if(handle) {
            blacklistedHandles.add(handle);
            unwhitelistHandle(handle)
        }
    }

    void blacklistTweet(Tweet tweet) {
        if(greylistedTweets.contains(tweet)) {
            greylistedTweets.remove(tweet)
            blacklistedTweets.add(tweet)
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
