package com.zaphlabs.knotty_online.ui.home

import com.zaphlabs.knotty_online.data.DataManager
import com.zaphlabs.knotty_online.data.remote.CallbackListener
import com.zaphlabs.knotty_online.ui.base.BaseViewModel

class HomeViewModel(private val dataManager: DataManager) : BaseViewModel() {

    val user by lazy { dataManager.currentUser() }
    var callbackListener: CallbackListener? = null

    fun logOutUser(){
        dataManager.logout()
    }
}