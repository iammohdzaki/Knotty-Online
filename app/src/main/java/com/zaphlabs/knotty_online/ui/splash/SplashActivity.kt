package com.zaphlabs.knotty_online.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.zaphlabs.knotty_online.R
import com.zaphlabs.knotty_online.ui.auth.LogInActivity
import com.zaphlabs.knotty_online.ui.auth.SignUpActivity
import com.zaphlabs.knotty_online.ui.base.BaseActivity
import com.zaphlabs.knotty_online.utils.SPLASH_WAIT_TIME

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        init()
    }

    private fun init(){
        Handler().postDelayed({
            startActivity(Intent(this@SplashActivity, SignUpActivity::class.java))
            finish()
        }, SPLASH_WAIT_TIME)
    }
}
