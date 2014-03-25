package com.pca


class WhiteList
{
    private def handleList = ["Elmer", "Buggs", "Jason"]    // the list of twitter handles on the white list

    // determine if a twitter handle is on the white list
    boolean isHandleInList(String handle)
    {
        // Find the handle in the list.  Use a case insensitive search
        def foundHandle = handleList.find { it.compareToIgnoreCase(handle) == 0 }

        return (foundHandle != null)
    }

}
