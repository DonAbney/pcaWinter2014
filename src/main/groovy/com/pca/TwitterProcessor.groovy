package com.pca

class TwitterProcessor {
    private Set<String> blacklistedHandles = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER)
    private Set<String> blacklistedWords = new HashSet<String>()
    private Set<String> whitelistedHandles = new HashSet<String>()

    void blacklistHandle(String handle) {
        if(handle) {
            blacklistedHandles.add(handle);
            unwhitelistHandle(handle)
        }
    }

    private def gatherCaseInsensitiveMatchingStringsFromIterable(String string, Collection collection) {
        collection.findAll{ string.toUpperCase() == it.toUpperCase() }
    }

    void blacklistWord(String handle) {}
    void whitelistHandle(String handle) {}

    void unblacklistHandle(String handle) {
        Collection found_handles = gatherCaseInsensitiveMatchingStringsFromIterable(handle, blacklistedHandles)
        found_handles.each{ found_handle -> blacklistedHandles.remove(found_handle) }
    }

    void unblacklistWord(String word) {}
    void unwhitelistHandle(String handle) {
        whitelistedHandles.remove(handle) // Just a stub to test blacklisting. Should get replaced by merge of card 128.
    }

    private void setBlacklistHandles(Set<String> blacklistedHandles) {}
    private void setBlacklistWords(Set<String> blacklistedWords) {}
    private void setWhitelistHandles(Set<String> whitelistedHandles) {}
}
