package com.zaphlabs.knotty_online.ui.onBoarding

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.auth.api.Auth
import com.zaphlabs.knotty_online.R
import com.zaphlabs.knotty_online.databinding.ActivityLogInBinding
import com.zaphlabs.knotty_online.ui.base.BaseActivity
import com.zaphlabs.knotty_online.ui.home.HomeActivity
import com.zaphlabs.knotty_online.utils.STATUS_CODES
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance
import org.kodein.di.android.kodein
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.ConnectionResult
import com.google.firebase.auth.GoogleAuthProvider
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient


class LogInActivity : BaseActivity(), KodeinAware, AuthListener,
    GoogleApiClient.OnConnectionFailedListener {

    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance()
    private lateinit var viewModel: AuthViewModel
    var mGoogleSignInClient: GoogleApiClient? = null
    val RC_SIGN_IN = 201

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityLogInBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_log_in)
        viewModel = ViewModelProviders.of(this, factory).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel
        viewModel.authListener = this
        configureGoogleSignIn()
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
        if (viewModel.user != null) {
            startActivity(Intent(this@LogInActivity, HomeActivity::class.java))
            finish()
        }
    }

    private fun configureGoogleSignIn() {
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()


        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleApiClient.Builder(this)
            .enableAutoManage(this /* FragmentActivity */, this)
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()
        mGoogleSignInClient!!.connect()
    }

    fun performGoogleSignIn(view: View) {
        //New Sign In
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleSignInClient);
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if(task.isSuccess) {
                val account = task.signInAccount
                viewModel.firebaseAuthWithGoogle(account!!)
            }else{
                Log.w("Google Sign In", "Google sign in failed")
            }
        }
    }

    override fun onConnectionFailed(p0: ConnectionResult) {

    }

}
