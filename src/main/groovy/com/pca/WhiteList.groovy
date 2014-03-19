package com.pca

class WhiteList
{
    def whiteList = ["Jason"]

    boolean isInWhiteList(String handle)
    {
        return whiteList.contains(handle)
    }
}
