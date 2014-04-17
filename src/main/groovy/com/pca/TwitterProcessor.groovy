package com.pca

class TwitterProcessor {
    private Set<String> blacklistedHandles = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER)
    private Set<String> blacklistedWords   = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER)
    private Set<String> whitelistedHandles = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER)

    void addBlackListHandle(String handle) {}
    void addBlackListWord(String word) {}
    void addWhiteListHandle(String handle) {
        if (handle) {
            whitelistedHandles.add(handle)
        }
    }

    void removeBlackListHandle(String handle) {}
    void removeBlackListWord(String word) {}
    void removeWhiteListHandle(String handle) {
        if (handle) {
            whitelistedHandles.remove(handle)
        }
    }

    private void setBlackListHandles(Set<String> blackListHandles) {}
    private void setBlackListWords(Set<String> blackListWords) {}
    private void setWhiteListHandles(Set<String> whiteListHandles) {}
}
