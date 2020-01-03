package com.zaphlabs.knotty_online.ui.editor

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.zaphlabs.knotty_online.R
import com.zaphlabs.knotty_online.data.remote.CallbackListener
import com.zaphlabs.knotty_online.databinding.ActivityEditorBinding
import com.zaphlabs.knotty_online.ui.base.BaseActivity
import com.zaphlabs.knotty_online.utils.STATUS_CODES.Companion.FAILED
import kotlinx.android.synthetic.main.activity_editor.*
import kotlinx.android.synthetic.main.layout_toolbar.view.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class EditorActivity : BaseActivity() ,CallbackListener, KodeinAware {

    override val kodein by kodein()
    private val factory : EditorViewModelFactory by instance()
    private lateinit var editorViewModel: EditorViewModel
    var forEdit=false

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
        }

        if(forEdit){
            AlterAccountToolbar.tvToolbarTitle.text = "Edit Account"
        }else{
            AlterAccountToolbar.tvToolbarTitle.text = "Add Account"
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
