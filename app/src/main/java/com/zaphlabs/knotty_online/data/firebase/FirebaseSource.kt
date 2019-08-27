package com.zaphlabs.knotty_online.data.firebase

import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Completable

class FirebaseSource {

    private val firebaseAuth:FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    /**
     * Login User To Application
     * @param email as User Email
     * @param password as User Password
     */
    fun login(email:String,password:String) = Completable.create { emitter ->
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
            if(!emitter.isDisposed){
                if(it.isSuccessful)
                    emitter.onComplete()
                else
                    emitter.onError(it.exception!!)
            }
        }
    }

    /**
     * Register User To Application
     * @param email as User Email
     * @param password as User Password
     */
    fun register(email: String,password: String) = Completable.create { emitter ->
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
            if(!emitter.isDisposed){
                if(it.isSuccessful)
                    emitter.onComplete()
                else
                    emitter.onError(it.exception!!)
            }
        }
    }

    /**
     * Log Out
     */
    fun logout() = firebaseAuth.signOut()

    /**
     * Current Logged In User
     */
    fun currentUser() = firebaseAuth.currentUser

}