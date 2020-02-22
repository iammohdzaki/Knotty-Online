package com.zaphlabs.knotty_online.ui.home

import android.util.Log
import com.zaphlabs.knotty_online.data.DataManager
import com.zaphlabs.knotty_online.data.model.User
import com.zaphlabs.knotty_online.data.remote.CallbackListener
import com.zaphlabs.knotty_online.data.remote.ResponseCallback
import com.zaphlabs.knotty_online.ui.base.BaseViewModel
import com.zaphlabs.knotty_online.utils.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

@Suppress("UNCHECKED_CAST")
class HomeViewModel(private val dataManager: DataManager) : BaseViewModel() {

    val user by lazy { dataManager.currentUser() }
    var callbackListener: CallbackListener? = null
    var responseCallback: ResponseCallback?= null
    //var accountList=ArrayList<UserAccount>()
    //disposable to dispose the Completable
    private val disposables = CompositeDisposable()

    fun logOutUser(){
        dataManager.logout()
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }
}