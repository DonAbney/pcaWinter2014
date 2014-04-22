package com.pca

class TwitterProcessor {
    private Set<String> blacklistedHandles = new HashSet<String>()
    private TreeSet<String> blacklistedWords = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER)
    private Set<String> whitelistedHandles = new HashSet<String>()

    void blacklistHandle(String handle) {}
    void blacklistWord(String word) {
        if(word) blacklistedWords.add(word)
    }
    void whitelistHandle(String handle) {}

    void unblacklistHandle(String handle) {}
    void unblacklistWord(String word) {}
    void unwhitelistHandle(String handle) {}

    boolean isWordBlacklisted(String word) {
        return word ? blacklistedWords.contains(word) : false
    }

    private TreeSet<String> getBlackListedWords() {}

    private void setBlacklistedHandles(Set<String> blacklistedHandles) {}
    private void setBlacklistedWords(Set<String> blacklistedWords) {}
    private void setWhitelistedHandles(Set<String> whitelistedHandles) {}
}
