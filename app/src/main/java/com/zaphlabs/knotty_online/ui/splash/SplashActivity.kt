package com.zaphlabs.knotty_online.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.zaphlabs.knotty_online.R
import com.zaphlabs.knotty_online.ui.onBoarding.LogInActivity
import com.zaphlabs.knotty_online.ui.base.BaseActivity
import com.zaphlabs.knotty_online.ui.customView.AlertDialog
import com.zaphlabs.knotty_online.utils.SPLASH_WAIT_TIME

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        init()
    }

    private fun init(){
        if(!isNetworkConnected()){
            AlertDialog.Builder(this@SplashActivity)
                .message(getString(R.string.no_internet_try_again))
                .button("Retry")
                .animName("noconnection.json")
                .listener { _, _ -> init() }
                .build().show()
            return
        }

        Handler().postDelayed({
            startActivity(Intent(this@SplashActivity, LogInActivity::class.java))
            finish()
        }, SPLASH_WAIT_TIME)
    }
}
