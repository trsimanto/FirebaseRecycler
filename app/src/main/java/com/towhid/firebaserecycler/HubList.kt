package com.towhid.firebaserecycler

class HubList(
    private var hubName: String

) {

    fun getHubName(): String {
        if (hubName.equals(null))
            return ""
        return hubName
    }
}