package com.pca

class TwitterProcessor {
    private TreeSet<String> blacklistedHandles = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER)
    private TreeSet<String> blacklistedWords = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER)
    private TreeSet<String> whitelistedHandles = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER)

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

    boolean isWordBlacklisted(String word) {
        return word ? blacklistedWords.contains(word) : false
    }

    private TreeSet<String> getBlackListedWords() {}

    private void setBlacklistedHandles(Set<String> blacklistedHandles) {}
    private void setBlacklistedWords(Set<String> blacklistedWords) {}
    private void setWhitelistedHandles(Set<String> whitelistedHandles) {}
}
