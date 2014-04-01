package com.pca

class WhiteList {
  private def handleList = []

  void addHandle(String handle) {
      handleList.add(handle)
  }

  boolean isHandleInList(String handle) {
    handleList.find { handle?.equalsIgnoreCase(it) }
  }

}
