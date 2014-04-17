package com.pca

class TwitterProcessor {

    private Set<String> blacklistedHandles = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER)
    private Set<String> blacklistedWords   = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER)
    private Set<String> whitelistedHandles = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER)

    void blacklistHandle(String handle) {}
    void blacklistWord(String word) {}
    void whitelistHandle(String handle) {
        if (handle) {
            whitelistedHandles.add(handle)
        }
    }

    void unblacklistHandle(String handle) {}
    void unblacklistWord(String word) {}
    void unwhitelistHandle(String handle) {
        if (handle) {
            whitelistedHandles.remove(handle)
        }
    }

    private void setBlacklistedHandles(Set<String> blacklistedHandles) {}
    private void setBlacklistedWords(Set<String> blacklistedWords) {}
    private void setWhitelistedHandles(Set<String> whitelistedHandles) {}
}
