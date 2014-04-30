package com.pca

/**
 * Created by Jason on 4/22/14.
 */
class BlacklistedHandleFilter
{
    private TreeSet<String> blacklistedHandles = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER)

    boolean isBlacklisted(Tweet tweet)
    {
        blacklistedHandles.contains(tweet.handle)
    }

    void blacklistHandle(String handle) {
        if(handle) {
            blacklistedHandles.add(handle);
        }
    }

    void unblacklistHandle(String handle) {
        //if (handle) {
            blacklistedHandles.remove(handle)
        //}
    }
}
