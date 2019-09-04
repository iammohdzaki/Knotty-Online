package com.zaphlabs.knotty_online.data.remote

interface CallbackListener {
    fun onStarted()
    fun onSuccess()
    fun onComplete()
    fun onFailure(message: String)
}