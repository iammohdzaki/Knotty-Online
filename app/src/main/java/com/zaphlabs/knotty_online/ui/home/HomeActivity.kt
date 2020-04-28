package com.zaphlabs.knotty_online.ui.home

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.zaphlabs.knotty_online.ui.base.BaseActivity
import com.zaphlabs.knotty_online.R
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : BaseActivity(){

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        navController= Navigation.findNavController(this,R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(nvBottom,navController)
    }

   /* override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tvLogOut -> {
                OptionsDialog.Builder(this@HomeActivity)
                    .message("Are you sure you want to logout ?")
                    .positiveButton("Yes")
                    .negativeButton("No")
                    .listener(object : OptionsDialog.Listener {
                        override fun performNegativeAction(purpose: Int, backpack: Bundle?) {}
                        override fun performPositiveAction(purpose: Int, backpack: Bundle?) {
                            viewModel.logOutUser()
                            startActivity(Intent(this@HomeActivity, LogInActivity::class.java))
                            finish()
                        }
                    }).build().show()
            }
        }
    }*/

}
