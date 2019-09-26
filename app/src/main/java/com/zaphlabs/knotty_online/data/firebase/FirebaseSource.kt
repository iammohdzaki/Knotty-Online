package com.zaphlabs.knotty_online.data.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.zaphlabs.knotty_online.data.model.User
import com.zaphlabs.knotty_online.data.model.UserAccount
import com.zaphlabs.knotty_online.utils.*
import io.reactivex.Completable
import java.util.*
import kotlin.collections.HashMap

class FirebaseSource {

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private val firestoreDb: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    /**
     * Login User To Application
     * @param email as User Email
     * @param password as User Password
     */
    fun login(email: String, password: String) = Completable.create { emitter ->
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (!emitter.isDisposed) {
                if (it.isSuccessful)
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
    fun register(email: String, password: String) = Completable.create { emitter ->
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (!emitter.isDisposed) {
                if (it.isSuccessful)
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

    /**
     * Forgot Password
     * @param email as Email Address
     */
    fun resetPassword(email: String) = Completable.create { emitter ->
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener {
            if (!emitter.isDisposed) {
                if (it.isSuccessful)
                    emitter.onComplete()
                else
                    emitter.onError(it.exception!!)
            }
        }
    }

    /**
     * Save Sign Up User Data
     * @param userData as User Data Model
     */
    fun saveUserData(userData: User) = Completable.create { emitter ->
        var documentReference = firestoreDb.collection(USER_COLLECTION).document(firebaseAuth.currentUser!!.uid)
        documentReference.set(userData).addOnCompleteListener {
            if (!emitter.isDisposed) {
                if (it.isSuccessful)
                    emitter.onComplete()
                else
                    emitter.onError(it.exception!!)
            }
        }
    }

    /**
     * Get User Data
     * @return collection reference
     */
    fun getUserData(): CollectionReference {
        return firestoreDb.collection(USER_COLLECTION)
    }

    /**
     * Delete User Data
     */
    fun deleteUserData() = Completable.create { emitter ->
        var documentReference =
            firestoreDb.collection(USER_COLLECTION).document(firebaseAuth.currentUser!!.uid)
        documentReference.delete().addOnCompleteListener {
            if (!emitter.isDisposed) {
                if (it.isSuccessful)
                    emitter.onComplete()
                else
                    emitter.onError(it.exception!!)
            }
        }
    }

    /**
     * Update User Data
     */
    fun updateUserData(userData: User) = Completable.create { emitter ->
        var documentReference =
            firestoreDb.collection(USER_COLLECTION).document(firebaseAuth.currentUser!!.uid)
        documentReference.set(userData).addOnCompleteListener {
            if (!emitter.isDisposed) {
                if (it.isSuccessful)
                    emitter.onComplete()
                else
                    emitter.onError(it.exception!!)
            }
        }
    }

    /**
     * Add User Account
     */
    fun addAccount(userAccount: UserAccount)=Completable.create { emitter ->

        var count=2434
        val accountData= hashMapOf(
            ACCOUNT_ID to userAccount.accountId,
            ACCOUNT_TITLE to userAccount.accountTitle,
            ACCOUNT_USER_NAME to userAccount.accountUserName,
            ACCOUNT_EMAIL to userAccount.accountEmail,
            ACCOUNT_PASSWORD to userAccount.accountPassword,
            ACCOUNT_NOTE to userAccount.accountNote,
            ACCOUNT_COLOR to userAccount.imageColor,
            ACCOUNT_STARRED to userAccount.accountStarred,
            ACCOUNT_TIME_STAMP to userAccount.accountTimeStamp
        )

        val accountDocs= hashMapOf(
            "accounts" to hashMapOf(
                count.toString() to accountData
            )
        )

        val documentReference=firestoreDb.collection(USER_COLLECTION).document(firebaseAuth.currentUser!!.uid)
        documentReference.set(accountDocs as Map<String, Any>, SetOptions.merge()).addOnCompleteListener {
            if (!emitter.isDisposed) {
                if (it.isSuccessful)
                    emitter.onComplete()
                else
                    emitter.onError(it.exception!!)
            }
        }
    }

}