package com.zaphlabs.knotty_online.ui.favorite

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import com.zaphlabs.knotty_online.R
import com.zaphlabs.knotty_online.data.model.User
import com.zaphlabs.knotty_online.data.model.UserAccount
import com.zaphlabs.knotty_online.data.remote.CallbackListener
import com.zaphlabs.knotty_online.data.remote.ResponseCallback
import com.zaphlabs.knotty_online.ui.base.BaseActivity
import com.zaphlabs.knotty_online.ui.home.HomeViewModel
import com.zaphlabs.knotty_online.ui.home.HomeViewModelFactory
import com.zaphlabs.knotty_online.ui.home.adapter.AccountAdapter
import com.zaphlabs.knotty_online.ui.home.adapter.RecyclerClickListener
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class FavoriteActivity : BaseActivity(), KodeinAware, View.OnClickListener, CallbackListener,
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
        setContentView(R.layout.activity_favorite)
    }

    override fun onClick(p0: View?) {

    }

    override fun onStarted() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSuccess() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onComplete() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onFailure(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemClick(position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStarClick(position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
