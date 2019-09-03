package com.zaphlabs.knotty_online.data.firebase

import com.google.firebase.firestore.FirebaseFirestore

class AccountSource(){

    private val firestoreDb: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }
}