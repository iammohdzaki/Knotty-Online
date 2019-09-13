package com.zaphlabs.knotty_online.ui.onBoarding

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.zaphlabs.knotty_online.R
import com.zaphlabs.knotty_online.databinding.ActivityForgotPasswordBinding
import com.zaphlabs.knotty_online.ui.base.BaseActivity
import com.zaphlabs.knotty_online.ui.customView.AlertDialog
import com.zaphlabs.knotty_online.utils.STATUS_CODES.Companion.FAILED
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class ForgotPasswordActivity : BaseActivity(),AuthListener,KodeinAware {

    override val kodein by kodein()
    private val factory : AuthViewModelFactory by instance()
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityForgotPasswordBinding = DataBindingUtil.setContentView(this,R.layout.activity_forgot_password)
        viewModel= ViewModelProviders.of(this,factory).get(AuthViewModel::class.java)
        binding.viewmodel=viewModel
        viewModel.authListener = this
    }

    override fun onStarted() {
        showProgress()
    }

    override fun onSuccess() {
        hideProgress()
        showDialog()
    }

    override fun onComplete() {}

    override fun onFailure(message: String) {
        hideProgress()
        showSnackbar(message,FAILED)
    }

    private fun showDialog(){
        AlertDialog.Builder(this@ForgotPasswordActivity)
            .message("A email has been sent to your email!")
            .button("Ok")
            .animName("email.json")
            .listener { _, _ -> finish() }
            .build().show()
        return
    }
}
