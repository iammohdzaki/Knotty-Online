package com.zaphlabs.knotty_online.data.remote

import com.zaphlabs.knotty_online.data.model.UserAccount

interface ResponseCallback {
    fun onDataReceived(accountList:ArrayList<UserAccount>)
}