package com.pca

class TwitterProcessor {
    private Set<String> blacklistedHandles = new HashSet<String>()
    private Set<String> blacklistedWords = new HashSet<String>()
    private Set<String> whitelistedHandles = new HashSet<String>()

    void blacklistHandle(String handle) {}
    void blacklistWord(String word) {}
    void whitelistHandle(String handle) {}

    void unblacklistHandle(String handle) {}
    void unblacklistWord(String word) {}
    void unwhitelistHandle(String handle) {}

    private void setBlacklistedHandles(Set<String> blacklistedHandles) {}
    private void setBlacklistedWords(Set<String> blacklistedWords) {}
    private void setWhitelistedHandles(Set<String> whitelistedHandles) {}
}
