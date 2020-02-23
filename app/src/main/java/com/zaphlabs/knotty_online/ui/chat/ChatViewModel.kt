package com.zaphlabs.knotty_online.ui.chat

import android.net.Uri
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.UploadTask
import com.zaphlabs.knotty_online.data.DataManager
import com.zaphlabs.knotty_online.data.model.Message
import com.zaphlabs.knotty_online.data.remote.CallbackListener
import com.zaphlabs.knotty_online.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.net.URI

/**
 * Developer : Mohammad Zaki
 * Created On : 23-02-2020
 */

class ChatViewModel(private val dataManager: DataManager):BaseViewModel() {

    val user by lazy { dataManager.currentUser() }
    var chatNavigator: ChatNavigator ? = null
    var callbackListener: CallbackListener ?= null

    //disposable to dispose the Completable
    private val disposables = CompositeDisposable()

    fun sendMessage(message: Message){
        callbackListener?.onStarted()
        val disposable=dataManager.sendMessage(message)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ callbackListener?.onSuccess() }, { callbackListener?.onFailure(it.message!!) })
        disposables.add(disposable)
    }

    fun getDatabaseReference():DatabaseReference{
        return dataManager.getDatabaseRefrence()
    }

    fun getUserId():String{
        return dataManager.currentUser()!!.uid
    }

     fun uploadImage(uri: Uri):String{
        var imageUrl:String = ""
         val result = dataManager.getStoreageRef().putFile(uri).addOnSuccessListener {
             if(it.task.isSuccessful){
                  imageUrl = it.storage.downloadUrl.toString()
             }
         }
        return imageUrl
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }

}