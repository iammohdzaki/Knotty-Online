package com.zaphlabs.knotty_online.ui.home

import android.util.Log
import com.zaphlabs.knotty_online.data.DataManager
import com.zaphlabs.knotty_online.data.model.UserAccount
import com.zaphlabs.knotty_online.data.remote.CallbackListener
import com.zaphlabs.knotty_online.data.remote.ResponseCallback
import com.zaphlabs.knotty_online.ui.base.BaseViewModel
import com.zaphlabs.knotty_online.utils.*

@Suppress("UNCHECKED_CAST")
class HomeViewModel(private val dataManager: DataManager) : BaseViewModel() {

    val user by lazy { dataManager.currentUser() }
    var callbackListener: CallbackListener? = null
    var responseCallback: ResponseCallback?= null
    var accountList= ArrayList<UserAccount>()

    fun getAllAccounts(){
        callbackListener!!.onStarted()
        dataManager.getUserData().get().addOnSuccessListener {
            documentSnapshot ->
            if (documentSnapshot.get("accounts") != null) {
                var data: HashMap<String, Any> = documentSnapshot.get("accounts") as HashMap<String,Any>
                for (i in data){
                    var accountData = data[i.key] as HashMap<String,Any>
                    var userAccount=UserAccount(
                        accountData[ACCOUNT_ID].toString(),
                        accountData[ACCOUNT_TITLE].toString(),
                        accountData[ACCOUNT_USER_NAME].toString(),
                        accountData[ACCOUNT_PASSWORD].toString(),
                        accountData[ACCOUNT_EMAIL].toString(),
                        accountData[ACCOUNT_TIME_STAMP].toString(),
                        accountData[ACCOUNT_NOTE].toString(),
                        accountData[ACCOUNT_STARRED].toString().toInt()
                    )
                    accountList.add(userAccount)
                }
                Log.d("DATA---", "AccountList:${accountList.size}")
            } else {
                Log.d("DATA---", "No such document")
            }
            responseCallback!!.onDataReceived(accountList)
            callbackListener!!.onSuccess()
        }.addOnFailureListener { exception ->
                Log.d("DATA---", "get failed with ", exception)
                callbackListener!!.onFailure(exception.printStackTrace().toString())
            }
    }

    fun logOutUser(){
        dataManager.logout()
    }
}