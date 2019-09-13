package com.zaphlabs.knotty_online.ui.onBoarding

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.zaphlabs.knotty_online.R
import com.zaphlabs.knotty_online.databinding.ActivitySignUpBinding
import com.zaphlabs.knotty_online.ui.base.BaseActivity
import com.zaphlabs.knotty_online.ui.home.HomeActivity
import com.zaphlabs.knotty_online.utils.STATUS_CODES.Companion.FAILED
import com.zaphlabs.knotty_online.utils.STATUS_CODES.Companion.SUCCESS
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
        showProgress()
    }

    override fun onSuccess() {
        viewModel.saveUserData()
    }

    override fun onFailure(message: String) {
        hideProgress()
        showSnackbar(message,FAILED)
    }

    override fun onComplete() {
        hideProgress()
        showSnackbar("Success", SUCCESS)
        startActivity(Intent(this@SignUpActivity,HomeActivity::class.java))
        finish()
    }
}
