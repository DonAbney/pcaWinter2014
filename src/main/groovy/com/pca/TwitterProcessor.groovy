package com.pca

class TwitterProcessor {
    private Set<String> blackListHandles = new HashSet<String>()
    private Set<String> blackListWords = new HashSet<String>()
    private Set<String> whiteListHandles = new HashSet<String>()

    void addBlackListHandle(String handle) {}
    void addBlackListWord(String word) {}
    void addWhiteListHandle(String handle) {}

    void removeBlackListHandle(String handle) {}
    void removeBlackListWord(String word) {}
    void removeWhiteListHandle(String handle) {}

    private void setBlackListHandles(Set<String> blackListHandles) {}
    private void setBlackListWords(Set<String> blackListWords) {}
    private void setWhiteListHandles(Set<String> whiteListHandles) {}
}
