package com.zaphlabs.knotty_online.ui.auth

interface AuthListener {
    fun onStarted()
    fun onSuccess()
    fun onComplete()
    fun onFailure(message: String)
}