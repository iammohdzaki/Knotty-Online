package com.zaphlabs.knotty_online.ui.home

import android.app.ActionBar
import android.app.Activity
import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.zaphlabs.knotty_online.data.model.User
import com.zaphlabs.knotty_online.data.model.UserAccount
import com.zaphlabs.knotty_online.data.remote.CallbackListener
import com.zaphlabs.knotty_online.data.remote.ResponseCallback
import com.zaphlabs.knotty_online.databinding.ActivityHomeBinding
import com.zaphlabs.knotty_online.ui.onBoarding.LogInActivity
import com.zaphlabs.knotty_online.ui.base.BaseActivity
import com.zaphlabs.knotty_online.ui.customView.OptionsDialog
import com.zaphlabs.knotty_online.ui.editor.EditorActivity
import com.zaphlabs.knotty_online.ui.home.adapter.AccountAdapter
import com.zaphlabs.knotty_online.ui.home.adapter.RecyclerClickListener
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.layout_side_menu.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import kotlinx.android.synthetic.main.layout_toolbar.view.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import android.app.Dialog
import android.text.method.PasswordTransformationMethod
import android.view.ViewGroup
import com.zaphlabs.knotty_online.R
import com.zaphlabs.knotty_online.utils.*
import com.zaphlabs.knotty_online.utils.STATUS_CODES
import kotlinx.android.synthetic.main.layout_user_account.*


class HomeActivity : BaseActivity(), KodeinAware, View.OnClickListener, CallbackListener,
    ResponseCallback,
    RecyclerClickListener {

    override val kodein by kodein()
    private val factory: HomeViewModelFactory by instance()
    private lateinit var viewModel: HomeViewModel
    private var mDrawerToggle: ActionBarDrawerToggle? = null
    private var accountAdapter: AccountAdapter? = null
    private var accountList = ArrayList<UserAccount>()
    private var dialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityHomeBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_home)
        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)
        binding.viewModel = viewModel
        viewModel.callbackListener = this
        viewModel.responseCallback = this
        init()
        setNavigationDrawer()
    }

    private fun init() {
        homeToolbar.tvToolbarTitle.text = "Accounts"
        ivToolbarImage.visibility = View.VISIBLE
        ivToolbarRightImage.visibility = View.VISIBLE
        shimmer_view_container.visibility = View.VISIBLE
        viewModel.getAllAccounts()
        setOnClickListeners(this@HomeActivity, ivToolbarImage, ivToolbarRightImage, addAccount)
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
    }


    private fun setUpRecyclerView() {
        rvManageAccount.layoutManager = LinearLayoutManager(this)
        accountAdapter = AccountAdapter(this, accountList, this)
        rvManageAccount.adapter = accountAdapter
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.addAccount -> {
                startActivityForResult(
                    Intent(this@HomeActivity, EditorActivity::class.java),
                    OPEN_EDITOR_SCREEN
                )
            }
            R.id.ivToolbarImage -> {
                //Navigation
                navDrawer.openDrawer(Gravity.LEFT)
            }
            R.id.ivToolbarRightImage -> {
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
        viewModel.getAllAccounts()
    }

    override fun onFailure(message: String) {
        shimmer_view_container.stopShimmerAnimation()
        slSidePlaceholder.stopShimmerAnimation()
        showSnackbar(message, STATUS_CODES.FAILED)
    }

    override fun onDataReceived(userData: User) {
        shimmer_view_container.visibility = View.GONE
        if (userData.accounts!!.size > 0) {
            rvManageAccount.visibility = View.VISIBLE
            noAccounts.visibility = View.GONE
        } else {
            rvManageAccount.visibility = View.GONE
            noAccounts.visibility = View.VISIBLE
        }
        this.accountList = userData.accounts!!
        groupUserData.visibility=View.VISIBLE
        slSidePlaceholder.visibility=View.GONE
        tvUserName.text=userData.name
        tvUserEmail.text=userData.email
        setUpRecyclerView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            OPEN_EDITOR_SCREEN -> {
                if (resultCode == Activity.RESULT_OK) {
                    viewModel.getAllAccounts()
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onItemClick(position: Int) {
        showAccountInfo(accountList[position])
    }

    override fun onStarClick(position: Int) {
        viewModel.starAccount(accountList[position].accountId,if(accountList[position].accountStarred == 0) 1 else 0)
    }

    /**
     * Show User Info Dialog
     */
    private fun showAccountInfo(userAccount: UserAccount){
        dialog= Dialog(this)
        dialog!!.setContentView(R.layout.layout_user_account)
        dialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog!!.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)

        //Set Data on Dialog
        dialog!!.tvAccountTitle.text=userAccount.accountTitle
        dialog!!.tvAccountUserName.text=userAccount.accountUserName
        dialog!!.tvAccountPassword.text=userAccount.accountPassword!!.decrypt(ENCRYPTION_KEY)
        dialog!!.tvAccountEmail.text=userAccount.accountEmail
        dialog!!.tvAccountNote.text=userAccount.accountNote
        dialog!!.ivStar.isSelected=userAccount.accountStarred == 1
        dialog!!.tvAccountPassword.transformationMethod = PasswordTransformationMethod()

        dialog!!.ivPasswordView.setOnClickListener {
            val isPasswordVisible=dialog!!.tvAccountPassword.transformationMethod == null
            if(isPasswordVisible){
                dialog!!.tvAccountPassword.transformationMethod = PasswordTransformationMethod()
                dialog!!.ivPasswordView.setImageResource(R.drawable.ic_hide_pass)
            }else{
                dialog!!.tvAccountPassword.transformationMethod = null
                dialog!!.ivPasswordView.setImageResource(R.drawable.ic_show_pass)
            }
        }

        dialog!!.ivEdit.setOnClickListener {
            dialog!!.dismiss()
            var intent=Intent(this@HomeActivity, EditorActivity::class.java)
            intent.putExtra("forEdit",true)
            intent.putExtra(USER_MODEL_DATA,userAccount)
            startActivityForResult(
                intent,
               OPEN_EDITOR_SCREEN
             )
        }

        dialog!!.show()
    }

}
