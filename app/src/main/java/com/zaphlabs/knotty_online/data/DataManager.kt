package com.zaphlabs.knotty_online.data

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.storage.StorageReference
import com.zaphlabs.knotty_online.data.firebase.FirebaseSource
import com.zaphlabs.knotty_online.data.model.Message
import com.zaphlabs.knotty_online.data.model.User

class DataManager(private val firebase: FirebaseSource){

    fun login(email: String, password: String) = firebase.login(email, password)

    fun register(email: String, password: String) = firebase.register(email, password)

    fun currentUser() = firebase.currentUser()

    fun logout() = firebase.logout()

    fun forgotPassword(email: String) = firebase.resetPassword(email)

    fun saveUserData(user: User) = firebase.saveUserData(user)

    fun getUserData():DocumentReference = firebase.getUserData()

    fun deleteUserAccount(accountId: String) = firebase.deleteUserData(accountId)

    fun sendMessage(message: Message) = firebase.sendMessage(message)

    fun getDatabaseRefrence():DatabaseReference = firebase.getDatabaseReference()

    fun firebaseAuthWithGoogle(account:GoogleSignInAccount) = firebase.firebaseAuthWithGoogle(account)

    fun getStoreageRef():StorageReference = firebase.uploadImage()

}