package com.zaphlabs.knotty_online.ui.home

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.zaphlabs.knotty_online.R
import com.zaphlabs.knotty_online.databinding.ActivityHomeBinding
import com.zaphlabs.knotty_online.ui.base.BaseActivity
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class HomeActivity : BaseActivity() ,KodeinAware{

    override val kodein by kodein()
    private val factory: HomeViewModelFactory by instance()
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding:ActivityHomeBinding= DataBindingUtil.setContentView(this,R.layout.activity_home)
        viewModel=ViewModelProviders.of(this,factory).get(HomeViewModel::class.java)
        binding.viewModel=viewModel

        showToast(viewModel.user?.email!!)
    }
}
