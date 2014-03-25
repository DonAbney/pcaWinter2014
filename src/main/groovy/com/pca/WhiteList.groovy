package com.pca

class WhiteList
{
    private def handleList = ["Elmer", "Buggs", "Jason"]

    boolean isHandleInList(String handle)
    {
        def foundHandle = handleList.find { it.compareToIgnoreCase(handle) == 0 }

        foundHandle != null
    }

}
