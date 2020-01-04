package com.zaphlabs.knotty_online.ui.editor

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.zaphlabs.knotty_online.R
import com.zaphlabs.knotty_online.data.model.UserAccount
import com.zaphlabs.knotty_online.data.remote.CallbackListener
import com.zaphlabs.knotty_online.databinding.ActivityEditorBinding
import com.zaphlabs.knotty_online.ui.base.BaseActivity
import com.zaphlabs.knotty_online.utils.ENCRYPTION_KEY
import com.zaphlabs.knotty_online.utils.STATUS_CODES.Companion.FAILED
import com.zaphlabs.knotty_online.utils.USER_MODEL_DATA
import com.zaphlabs.knotty_online.utils.decrypt
import kotlinx.android.synthetic.main.activity_editor.*
import kotlinx.android.synthetic.main.layout_toolbar.view.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class EditorActivity : BaseActivity() ,CallbackListener, KodeinAware {

    override val kodein by kodein()
    private val factory : EditorViewModelFactory by instance()
    private lateinit var editorViewModel: EditorViewModel
    private var forEdit=false
    private var userAccount:UserAccount ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding:ActivityEditorBinding= DataBindingUtil.setContentView(this,R.layout.activity_editor)
        editorViewModel= ViewModelProviders.of(this,factory).get(EditorViewModel::class.java)
        binding.viewModel=editorViewModel
        editorViewModel.callbackListener = this
        init()
    }

    private fun init(){
        if(intent.hasExtra("forEdit")){
            forEdit = intent.getBooleanExtra("forEdit",false)
            userAccount=intent.getParcelableExtra<UserAccount>(USER_MODEL_DATA)
        }
        setData()
    }

    private fun setData(){
        if(forEdit){
            AlterAccountToolbar.tvToolbarTitle.text = "Edit Account"
            if(userAccount != null){
                editorViewModel.accountTitle=userAccount!!.accountTitle
                editorViewModel.accountUserName=userAccount!!.accountUserName
                editorViewModel.accountPassword = userAccount!!.accountPassword!!.decrypt(ENCRYPTION_KEY)
                editorViewModel.accountEmail = userAccount!!.accountEmail
                editorViewModel.accountNote = userAccount!!.accountNote
                editorViewModel.accountId=userAccount!!.accountId
                editorViewModel.isStar=userAccount!!.accountStarred
                editorViewModel.forEdit=forEdit
            }
            btnAlterAccount.text="Update Account"
        }else{
            AlterAccountToolbar.tvToolbarTitle.text = "Add Account"
            btnAlterAccount.text="Add Account"
        }
    }

    override fun onStarted() {
        showProgress()
    }

    override fun onSuccess() {
        hideProgress()
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun onComplete() {}

    override fun onFailure(message: String) {
        hideProgress()
        showSnackbar(message,FAILED)
    }


}
