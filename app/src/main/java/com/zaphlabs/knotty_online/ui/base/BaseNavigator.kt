package com.zaphlabs.knotty_online.ui.base

import android.view.View
import com.zaphlabs.knotty_online.ui.customView.CustomAlertDialog
import com.zaphlabs.knotty_online.ui.customView.MaterialEditText

interface BaseNavigator {

    fun hideKeyboard()
    fun isNetworkConnected():Boolean
    fun onBackPress()
    fun showProgress()
    fun hideProgress()
    fun showSnackbar(message: String, barStatus: Int)
    fun showSnackbar(messageResId: Int, barStatus: Int)
    fun showToast(message: String)
    fun showToast(messageResId: Int)
    fun setOnClickListeners(onClickListener: View.OnClickListener, vararg views: View)
    fun showErrorOnEditText(editText: MaterialEditText, errorMessage: String)
    fun showErrorOnEditText(editText: MaterialEditText, errorMessageResId: Int)
    fun showConfirmAlertDialog(titleResId: String, optionaImage: String, messageResId: String, positiveTextId: String, negativeTextId: String, onClickListener: CustomAlertDialog.CustomDialogInterface.OnClickListener)
    fun showConfirmAlertDialog(title: String, imageView: String, message: String, onClickListener: CustomAlertDialog.CustomDialogInterface.OnClickListener)
    fun showConfirmAlertDialog(titleResId: Int, imageView: Int, messageResId: Int, onClickListener: CustomAlertDialog.CustomDialogInterface.OnClickListener)

}