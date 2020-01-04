package com.zaphlabs.knotty_online.data

import com.google.firebase.firestore.DocumentReference
import com.zaphlabs.knotty_online.data.firebase.FirebaseSource
import com.zaphlabs.knotty_online.data.model.User
import com.zaphlabs.knotty_online.data.model.UserAccount

class DataManager(private val firebase: FirebaseSource){

    fun login(email: String, password: String) = firebase.login(email, password)

    fun register(email: String, password: String) = firebase.register(email, password)

    fun currentUser() = firebase.currentUser()

    fun logout() = firebase.logout()

    fun forgotPassword(email: String) = firebase.resetPassword(email)

    fun saveUserData(user: User) = firebase.saveUserData(user)

    fun getUserData():DocumentReference = firebase.getUserData()

    fun deleteUserData() = firebase.deleteUserData()

    fun updateUserData(user: User) = firebase.updateUserData(user)

    fun addUserAccount(userAccount: UserAccount) = firebase.addAccount(userAccount)

    fun starAccount(accountId:String,isStar:Int) = firebase.starAccount(accountId,isStar)

}