package com.zaphlabs.knotty_online.data.remote

import com.zaphlabs.knotty_online.data.model.User

interface ResponseCallback {
    fun onDataReceived(userData: User)
}