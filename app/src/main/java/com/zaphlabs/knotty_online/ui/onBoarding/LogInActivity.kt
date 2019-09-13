package com.zaphlabs.knotty_online.ui.onBoarding

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.zaphlabs.knotty_online.R
import com.zaphlabs.knotty_online.databinding.ActivityLogInBinding
import com.zaphlabs.knotty_online.ui.base.BaseActivity
import com.zaphlabs.knotty_online.ui.home.HomeActivity
import com.zaphlabs.knotty_online.utils.STATUS_CODES
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance
import org.kodein.di.android.kodein

class LogInActivity : BaseActivity(),KodeinAware,AuthListener{

    override val kodein by kodein()
    private val factory : AuthViewModelFactory by instance()
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding:ActivityLogInBinding=DataBindingUtil.setContentView(this,R.layout.activity_log_in)
        viewModel=ViewModelProviders.of(this,factory).get(AuthViewModel::class.java)
        binding.viewmodel=viewModel
        viewModel.authListener = this
    }

    override fun onStarted() {
        showProgress()
    }

    override fun onSuccess() {
        hideProgress()
        startActivity(Intent(this@LogInActivity, HomeActivity::class.java))
        finish()
    }

    override fun onFailure(message: String) {
        hideProgress()
        showSnackbar(message, STATUS_CODES.FAILED)
    }

    override fun onComplete() {}

    override fun onStart() {
        super.onStart()
        if(viewModel.user != null){
            startActivity(Intent(this@LogInActivity, HomeActivity::class.java))
            finish()
        }
    }

}
