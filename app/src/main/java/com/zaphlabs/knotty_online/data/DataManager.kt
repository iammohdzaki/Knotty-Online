package com.zaphlabs.knotty_online.data

import com.zaphlabs.knotty_online.data.firebase.FirebaseSource

class DataManager(private val firebase: FirebaseSource){

    fun login(email: String, password: String) = firebase.login(email, password)

    fun register(email: String, password: String) = firebase.register(email, password)

    fun currentUser() = firebase.currentUser()

    fun logout() = firebase.logout()

}