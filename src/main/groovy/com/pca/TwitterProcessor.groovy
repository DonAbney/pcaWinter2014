package com.pca

class TwitterProcessor {
    private Set<String> blackListUsers = new HashSet<String>()
    private Set<String> blackListWords = new HashSet<String>()
    private Set<String> whiteListUsers = new HashSet<String>()

    void addBlackListUser(String handle)
    {
        def handle_in_list = stringCaseInsensitivelyInList(handle, blackListUsers)
        if(handle != null && !handle_in_list)
        {
            blackListUsers.add(handle);
        }

    }

    private boolean stringCaseInsensitivelyInList(string, list)
    {
        return list.any{string.toUpperCase() == it.toUpperCase()}
    }

    void addBlackListWord(String user) {}
    void addWhiteListUser(String user) {}

    void removeBlackListUser(String user) {}
    void removeBlackListWord(String user) {}
    void removeWhiteListUser(String user) {}

    private void setBlackListUsers(Set<String> blackListUsers) {}
    private void setBlackListWords(Set<String> blackListWords) {}
    private void setWhiteListUsers(Set<String> whiteListUsers) {}
}
