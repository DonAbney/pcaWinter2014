package com.pca


class BlacklistedWordFilter
{
    private TreeSet<String> blacklistedWords = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER)
    private void setBlacklistedWords(Set<String> blacklistedWords) {}

    boolean isBlacklisted(Tweet tweet)
    {
        return blacklistedWords.any{tweet.text.contains(it)}
    }

    void blacklistWord(String word) {
        if(word) blacklistedWords.add(word)
    }

    void unblacklistWord(String word) {
        blacklistedWords.remove(word)
    }
}
