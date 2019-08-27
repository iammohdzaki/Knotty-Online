package com.zaphlabs.knotty_online.ui.base

import android.content.Context
import androidx.fragment.app.Fragment
import com.zaphlabs.knotty_online.ui.customView.MaterialEditText

abstract class BaseFragment : Fragment(),BaseNavigator {

    private lateinit var mActivity: BaseActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as BaseActivity
    }

    override fun hideKeyboard() {
        mActivity.hideKeyboard()
    }

    override fun showErrorOnEditText(editText: MaterialEditText, errorMessage: String) {
        mActivity.showErrorOnEditText(editText, errorMessage)
    }

    override fun showErrorOnEditText(editText: MaterialEditText, errorMessageResId: Int) {
        mActivity.showErrorOnEditText(editText, errorMessageResId)
    }

    override fun showToast(messageResId: Int) {
        mActivity.showToast(messageResId)
    }

    override fun showToast(message: String) {
        mActivity.showToast(message)
    }
}