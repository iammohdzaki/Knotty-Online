package com.zaphlabs.knotty_online.data.firebase

import android.net.Uri
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.zaphlabs.knotty_online.data.model.Message
import com.zaphlabs.knotty_online.data.model.User
import com.zaphlabs.knotty_online.utils.*
import io.reactivex.Completable
import java.util.*
import kotlin.collections.HashMap

class FirebaseSource {

    val TAG="Firebase Source"

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private val firestoreDb: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    private val firebaseDb:FirebaseDatabase by lazy {
        FirebaseDatabase.getInstance()
    }

    private val firebaseStorage:FirebaseStorage by lazy {
        FirebaseStorage.getInstance()
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
    fun getUserData(): DocumentReference {
        return firestoreDb.collection(USER_COLLECTION).document(firebaseAuth.currentUser!!.uid)
    }

    /**
     * Delete User Data
     */
    fun deleteUserData(accountId: String) = Completable.create { emitter ->
        firestoreDb.collection(USER_COLLECTION).document(firebaseAuth.currentUser!!.uid).update(
            mapOf(
                "accounts.$accountId" to FieldValue.delete()
            )
        ).addOnCompleteListener {
            if (!emitter.isDisposed) {
                if (it.isSuccessful)
                    emitter.onComplete()
                else
                    emitter.onError(it.exception!!)
            }
        }
    }

     fun firebaseAuthWithGoogle(acct: GoogleSignInAccount)  = Completable.create { emitter ->
         //Log.d(TAG, "firebaseAuthWithGoogle:" + acct.id!!)

         val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
         firebaseAuth.signInWithCredential(credential)
             .addOnCompleteListener { task ->

                 if (!emitter.isDisposed) {
                     if (task.isSuccessful) {
                         //Log.d(TAG, "signInWithCredential:success")
                         emitter.onComplete()
                     }
                     else {
                         emitter.onError(task.exception!!)
                         //Log.w(TAG, "signInWithCredential:failure", task.exception!!)
                     }
                 }
             }
     }

    fun getMessages(){
        var databaseReference=firebaseDb.reference.child("messages")
    }

    fun getDatabaseReference():DatabaseReference{
        return firebaseDb.reference.child("message")
    }

    /**
     * Send Message
     */
    fun sendMessage(message: Message) = Completable.create{emitter ->
        firebaseDb.reference.child("message").push().setValue(message)
            .addOnCompleteListener {
                if(!emitter.isDisposed){
                    if(it.isSuccessful)
                        emitter.onComplete()
                    else
                        emitter.onError(it.exception!!)
                }
            }
    }

    fun uploadImage():StorageReference{
        return firebaseStorage.reference.child("chat_photos")
    }

}