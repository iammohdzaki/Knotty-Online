package com.zaphlabs.knotty_online.ui.home

import android.os.Bundle
import com.zaphlabs.knotty_online.R
import com.zaphlabs.knotty_online.ui.auth.AuthViewModelFactory
import com.zaphlabs.knotty_online.ui.base.BaseActivity
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class HomeActivity : BaseActivity() ,KodeinAware{

    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }
}
