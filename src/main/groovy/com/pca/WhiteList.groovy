package com.pca

class WhiteList {
  private def handleList = ["Elmer", "Buggs", "Jason"]

  boolean isHandleInList(String handle) {
    handleList.find { handle.equalsIgnoreCase(it) }
  }

}
