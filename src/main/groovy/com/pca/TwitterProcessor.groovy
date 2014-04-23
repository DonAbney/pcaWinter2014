package com.pca

class TwitterProcessor {

    private Set<String> blacklistedHandles = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER)
    private Set<String> blacklistedWords   = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER)
    private Set<String> whitelistedHandles = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER)

    void blacklistHandle(String handle) {
        if(handle) {
            blacklistedHandles.add(handle);
            unwhitelistHandle(handle)
        }
    }

    void blacklistWord(String word) {}
    void whitelistHandle(String handle) {
        if (handle) {
            whitelistedHandles.add(handle)
        }
    }

    void unblacklistHandle(String handle) {
        blacklistedHandles.remove(handle)
    }

    void unblacklistWord(String word) {}
    void unwhitelistHandle(String handle) {
        if (handle) {
            whitelistedHandles.remove(handle)
        }
    }

    private void setBlacklistedHandles(Set<String> blacklistedHandles) {}
    private void setBlacklistedWords(Set<String> blacklistedWords) {}
    private void setWhitelistedHandles(Set<String> whitelistedHandles) {}

    FilteredTweets gatherFilteredTweets() {
        new FilteredTweets()
    }
}
