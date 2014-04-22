package com.pca


class WhitelistedHandleFilter
{
    private TreeSet<String> whitelistedHandles = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER)

    boolean isWhitelisted(Tweet tweet)
    {
        whitelistedHandles.contains(tweet.handle)
    }

    void whitelistHandle(String handle)
    {
        if (handle)
        {
            whitelistedHandles.add(handle)
        }
    }

    void unwhitelistHandle(String handle)
    {
        if (handle)
        {
            whitelistedHandles.remove(handle)
        }
    }

    private void setWhitelistedHandles(Set<String> whitelistedHandles) {}
}
