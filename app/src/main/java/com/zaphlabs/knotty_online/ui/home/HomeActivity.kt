package com.zaphlabs.knotty_online.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.zaphlabs.knotty_online.data.remote.CallbackListener
import com.zaphlabs.knotty_online.databinding.ActivityHomeBinding
import com.zaphlabs.knotty_online.ui.onBoarding.LogInActivity
import com.zaphlabs.knotty_online.ui.base.BaseActivity
import com.zaphlabs.knotty_online.ui.customView.OptionsDialog
import com.zaphlabs.knotty_online.ui.home.adapter.ChatAdapter
import com.zaphlabs.knotty_online.ui.home.adapter.RecyclerClickListener
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.layout_side_menu.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import android.app.Dialog
import android.view.animation.AnimationUtils
import com.zaphlabs.knotty_online.R
import com.zaphlabs.knotty_online.data.model.Chat
import com.zaphlabs.knotty_online.ui.chat.ChatActivity
import com.zaphlabs.knotty_online.utils.*
import com.zaphlabs.knotty_online.utils.STATUS_CODES


class HomeActivity : BaseActivity(), KodeinAware, View.OnClickListener, CallbackListener,
    RecyclerClickListener {


    override fun onItemClick(position: Int) {

    }

    override val kodein by kodein()
    private val factory: HomeViewModelFactory by instance()
    private lateinit var viewModel: HomeViewModel
    private var mDrawerToggle: ActionBarDrawerToggle? = null
    private var chatAdapter: ChatAdapter? = null
    private var chatList = ArrayList<Chat>()
    private var dialog: Dialog? = null
    private var isOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityHomeBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_home)
        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)
        binding.viewModel = viewModel
        viewModel.callbackListener = this
        init()
        setNavigationDrawer()
        setUpRecyclerView()
        setUpChatListener()
    }

    private fun init() {
        shimmer_view_container.visibility = View.VISIBLE
        setOnClickListeners(this@HomeActivity, fbAddEvent, fabAccount, fbTasks)
    }

    private fun setNavigationDrawer() {
        mDrawerToggle =
            object : ActionBarDrawerToggle(this, navDrawer, R.string.app_name, R.string.app_name) {
                override fun onDrawerClosed(view: View) {
                }

                override fun onDrawerOpened(drawerView: View) {
                }

                override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                    super.onDrawerSlide(drawerView, slideOffset)
                    coordinator.translationX = slideOffset * drawerView.width
                    navDrawer.bringChildToFront(drawerView)
                    navDrawer.requestLayout()
                }
            }
        navDrawer.addDrawerListener(mDrawerToggle as ActionBarDrawerToggle)

        bottomAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.settings -> {
                    true
                }
                else -> false
            }
        }

        bottomAppBar.setNavigationOnClickListener {
            navDrawer.openDrawer(Gravity.LEFT)
        }
    }


    private fun setUpRecyclerView() {
        rvManageAccount.layoutManager = LinearLayoutManager(this)
        chatAdapter = ChatAdapter(this, chatList, this)
        rvManageAccount.adapter = chatAdapter
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.fbAddEvent -> {
                if (isOpen) {
                    fbAddEvent.startAnimation(
                        AnimationUtils.loadAnimation(
                            this@HomeActivity,
                            R.anim.rotate_antoclockwise
                        )
                    )
                    fbTasks.visibility = View.GONE
                    fabAccount.visibility = View.GONE
                    fbTasks.startAnimation(
                        AnimationUtils.loadAnimation(
                            this@HomeActivity,
                            R.anim.fab_close
                        )
                    )
                    fabAccount.startAnimation(
                        AnimationUtils.loadAnimation(
                            this@HomeActivity,
                            R.anim.fab_close
                        )
                    )
                    isOpen = false
                } else {
                    fbAddEvent.startAnimation(
                        AnimationUtils.loadAnimation(
                            this@HomeActivity,
                            R.anim.rotate_clockwise
                        )
                    )
                    fbTasks.visibility = View.VISIBLE
                    fabAccount.visibility = View.VISIBLE
                    fbTasks.startAnimation(
                        AnimationUtils.loadAnimation(
                            this@HomeActivity,
                            R.anim.fab_open
                        )
                    )
                    fabAccount.startAnimation(
                        AnimationUtils.loadAnimation(
                            this@HomeActivity,
                            R.anim.fab_open
                        )
                    )
                    isOpen = true
                }

            }
            R.id.fbTasks -> {
                startActivity(Intent(this@HomeActivity,ChatActivity::class.java))
            }
            R.id.fabAccount -> {

            }
            R.id.tvFavorites -> {
            }
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
    }

    override fun onStarted() {
        shimmer_view_container.startShimmerAnimation()
        slSidePlaceholder.startShimmerAnimation()
    }

    override fun onSuccess() {
        shimmer_view_container.stopShimmerAnimation()
        slSidePlaceholder.stopShimmerAnimation()
    }

    override fun onComplete() {
    }

    override fun onFailure(message: String) {
        shimmer_view_container.stopShimmerAnimation()
        slSidePlaceholder.stopShimmerAnimation()
        showSnackbar(message, STATUS_CODES.FAILED)
    }

    private fun setUpChatListener(){

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            OPEN_EDITOR_SCREEN -> {
                if (resultCode == Activity.RESULT_OK) {
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onStarClick(position: Int) {

    }

}
