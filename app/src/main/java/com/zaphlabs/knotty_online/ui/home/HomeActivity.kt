package com.zaphlabs.knotty_online.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.zaphlabs.knotty_online.R
import com.zaphlabs.knotty_online.data.remote.CallbackListener
import com.zaphlabs.knotty_online.databinding.ActivityHomeBinding
import com.zaphlabs.knotty_online.ui.auth.LogInActivity
import com.zaphlabs.knotty_online.ui.base.BaseActivity
import com.zaphlabs.knotty_online.ui.customView.OptionsDialog
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.layout_side_menu.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import kotlinx.android.synthetic.main.layout_toolbar.view.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class HomeActivity : BaseActivity(), KodeinAware, View.OnClickListener, CallbackListener {

    override val kodein by kodein()
    private val factory: HomeViewModelFactory by instance()
    private lateinit var viewModel: HomeViewModel
    private var mDrawerToggle: ActionBarDrawerToggle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityHomeBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_home)
        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)
        binding.viewModel = viewModel
        viewModel.callbackListener = this
        init()
        setNavigationDrawer()
    }

    private fun init() {
        homeToolbar.tvToolbarTitle.text = "Accounts"
        ivToolbarImage.visibility = View.VISIBLE
        ivToolbarRightImage.visibility = View.VISIBLE
        setOnClickListeners(this@HomeActivity, ivToolbarImage, ivToolbarRightImage)
    }

    private fun setNavigationDrawer() {
        mDrawerToggle =
            object : ActionBarDrawerToggle(this, navDrawer, R.string.app_name, R.string.app_name) {
                override fun onDrawerClosed(view: View) {
                    supportInvalidateOptionsMenu()
                }

                override fun onDrawerOpened(drawerView: View) {
                    supportInvalidateOptionsMenu()
                }

                override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                    super.onDrawerSlide(drawerView, slideOffset)
                    coordinator.translationX = slideOffset * drawerView.width
                    navDrawer.bringChildToFront(drawerView)
                    navDrawer.requestLayout()
                }
            }
        navDrawer.addDrawerListener(mDrawerToggle as ActionBarDrawerToggle)
    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ivToolbarImage -> {
                //Navigation
                navDrawer.openDrawer(Gravity.LEFT)
            }
            R.id.ivToolbarRightImage -> {
            }
            R.id.tvFavorites -> {
                sliderTabsSelector(R.id.tvFavorites)
            }
            R.id.tvContactUs -> {
                sliderTabsSelector(R.id.tvContactUs)
            }
            R.id.tvRate -> {
                sliderTabsSelector(R.id.tvRate)
            }
            R.id.tvFeedback -> {
                sliderTabsSelector(R.id.tvFeedback)
            }
            R.id.tvLogOut -> {
                sliderTabsSelector(R.id.tvLogOut)
                OptionsDialog.Builder(this@HomeActivity)
                    .message("Are you sure you want to logout ?")
                    .positiveButton("Yes")
                    .negativeButton("No")
                    .listener(object : OptionsDialog.Listener {
                        override fun performNegativeAction(purpose: Int, backpack: Bundle?) {

                        }

                        override fun performPositiveAction(purpose: Int, backpack: Bundle?) {
                            viewModel.logOutUser()
                            startActivity(Intent(this@HomeActivity, LogInActivity::class.java))
                            finish()
                        }
                    }).build().show()
            }
        }
    }

    private fun sliderTabsSelector(id: Int) {
        tvFavorites.isSelected = false
        tvContactUs.isSelected = false
        tvRate.isSelected = false
        tvLogOut.isSelected = false
        tvFeedback.isSelected = false

        when (id) {
            R.id.tvFavorites -> {
                handleNavigationBackground(tvFavorites, tvContactUs, tvRate, tvLogOut, tvFeedback)
            }

            R.id.tvContactUs -> {
                handleNavigationBackground(tvContactUs, tvFavorites, tvRate, tvLogOut, tvFeedback)
            }

            R.id.tvRate -> {
                handleNavigationBackground(tvRate, tvFavorites, tvContactUs, tvLogOut, tvFeedback)
            }

            R.id.tvLogOut -> {
                handleNavigationBackground(tvLogOut, tvFeedback, tvFavorites, tvContactUs, tvRate)
            }
            R.id.tvFeedback -> {
                handleNavigationBackground(tvFeedback, tvLogOut, tvRate, tvContactUs, tvFavorites)
            }
        }

    }


    private fun handleNavigationBackground(mainView: View, vararg otherViews: View) {
        mainView.isSelected = true
        (mainView as TextView).setTextColor(resources.getColor(R.color.colorAccent))
        (mainView as TextView).compoundDrawables[0].setTint(resources.getColor(R.color.colorAccent))
        mainView.setBackgroundResource(R.drawable.selector_navigation_click)

        for (i in otherViews) {
            (i as TextView).setTextColor(resources.getColor(R.color.black))
            (i as TextView).compoundDrawables[0].setTint(resources.getColor(R.color.gray))
            i.background = null
            i.isSelected = false
        }

    }

    override fun onStarted() {
    }

    override fun onSuccess() {
    }

    override fun onComplete() {
    }

    override fun onFailure(message: String) {
    }

}
