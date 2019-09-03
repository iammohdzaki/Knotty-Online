package com.zaphlabs.knotty_online.ui.auth

import android.content.Intent
import android.view.View
import com.zaphlabs.knotty_online.data.DataManager
import com.zaphlabs.knotty_online.data.model.User
import com.zaphlabs.knotty_online.ui.base.BaseViewModel
import com.zaphlabs.knotty_online.ui.home.HomeActivity
import com.zaphlabs.knotty_online.utils.ENCRYPTION_KEY
import com.zaphlabs.knotty_online.utils.encrypt
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class AuthViewModel(private val manager: DataManager) : BaseViewModel() {

    var name: String? = null
    var email: String? = null
    var password: String? = null
    var confirmPassword: String? = null

    var authListener: AuthListener? = null

    //disposable to dispose the Completable
    private val disposables = CompositeDisposable()

    val user by lazy { manager.currentUser() }

    fun loginUser() {
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure("Invalid email or password")
            return
        }


        authListener?.onStarted()
        val disposable = manager.login(email!!, password!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                //sending a success callback
                authListener?.onSuccess()
            }, {
                //sending a failure callback
                authListener?.onFailure(it.message!!)
            })
        disposables.add(disposable)
    }

    fun signUpUser() {
        if (email.isNullOrEmpty() || password.isNullOrEmpty() || name.isNullOrEmpty() || confirmPassword.isNullOrEmpty()) {
            authListener?.onFailure("Please input all values")
            return
        }

        if(!password.equals(confirmPassword)){
            authListener?.onFailure("Password do not match!")
            return
        }

        authListener?.onStarted()
        val disposable = manager.register(email!!, password!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                authListener?.onSuccess()
            }, {
                authListener?.onFailure(it.message!!)
            })
        disposables.add(disposable)
    }

    fun saveUserData() {
        var user = User(name, email, password!!.encrypt(ENCRYPTION_KEY))

        authListener?.onStarted()
        val disposable = manager.saveUserData(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                authListener?.onComplete()
            }, {
                authListener?.onFailure(it.message!!)
            })
        disposables.add(disposable)
    }

    fun goToSignUp(view: View) {
        Intent(view.context, SignUpActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    fun goToLogin(view: View) {
        Intent(view.context, LogInActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    fun goToHome(view: View) {
        Intent(view.context, HomeActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}