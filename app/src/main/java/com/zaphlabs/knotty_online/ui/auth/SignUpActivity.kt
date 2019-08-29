package com.zaphlabs.knotty_online.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.zaphlabs.knotty_online.R
import com.zaphlabs.knotty_online.databinding.ActivitySignUpBinding
import com.zaphlabs.knotty_online.ui.base.BaseActivity
import com.zaphlabs.knotty_online.ui.home.HomeActivity
import com.zaphlabs.knotty_online.utils.STATUS_CODES.Companion.FAILED
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class SignUpActivity : BaseActivity() ,AuthListener,KodeinAware{

    override val kodein by kodein()
    private val factory:AuthViewModelFactory by instance()

    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val  binding:ActivitySignUpBinding= DataBindingUtil.setContentView(this,R.layout.activity_sign_up)
        viewModel = ViewModelProviders.of(this,factory).get(AuthViewModel::class.java)
        binding.viewmodel=viewModel
        viewModel.authListener=this
    }

    override fun onStarted() {
        //Progress Bar Visible
        Intent(this, HomeActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(it)
        }
    }

    override fun onSuccess() {
        //Progress BAr Gone
       //Go to Home
    }

    override fun onFailure(message: String) {
        //Progress BAr Gone
        showSnackbar(message,FAILED)
    }
}
