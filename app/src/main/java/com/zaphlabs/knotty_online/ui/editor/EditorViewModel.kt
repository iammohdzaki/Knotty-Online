package com.zaphlabs.knotty_online.ui.editor

import com.zaphlabs.knotty_online.data.DataManager
import com.zaphlabs.knotty_online.data.model.UserAccount
import com.zaphlabs.knotty_online.data.remote.CallbackListener
import com.zaphlabs.knotty_online.ui.base.BaseViewModel
import com.zaphlabs.knotty_online.utils.ENCRYPTION_KEY
import com.zaphlabs.knotty_online.utils.encrypt
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class EditorViewModel(private val manager: DataManager) : BaseViewModel() {

    var accountTitle:String ?= null
    var accountUserName:String ?= null
    var accountEmail:String ?= null
    var accountPassword:String ?= null
    var accountNote:String ?= null
    var callbackListener: CallbackListener? = null
    //disposable to dispose the Completable
    private val disposables = CompositeDisposable()

    fun addAccount(){
        var user= UserAccount(0,"Google","Zaki","zaki123".encrypt(ENCRYPTION_KEY),"zaki@gmail.com",
            "10/19","Testing",1,-1)

        callbackListener?.onStarted()
        val disposable=manager.addUserAccount(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ callbackListener?.onSuccess() },{ callbackListener?.onFailure(it.message!!)})

        disposables.add(disposable)
    }

}