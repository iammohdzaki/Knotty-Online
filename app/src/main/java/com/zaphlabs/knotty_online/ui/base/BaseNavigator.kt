package com.zaphlabs.knotty_online.ui.base

import com.zaphlabs.knotty_online.ui.customView.CustomAlertDialog
import com.zaphlabs.knotty_online.ui.customView.MaterialEditText

interface BaseNavigator {

    fun hideKeyboard()
    fun isNetworkConnected()
    fun onBackPress()
    fun showSnackbar(message: String, barStatus: Int)
    fun showSnackbar(messageResId: Int, barStatus: Int)
    fun showToast(message: String)
    fun showToast(messageResId: Int)
    fun showErrorOnEditText(editText: MaterialEditText, errorMessage: String)
    fun showErrorOnEditText(editText: MaterialEditText, errorMessageResId: Int)
    fun showConfirmAlertDialog(titleResId: String, optionaImage: String, messageResId: String, positiveTextId: String, negativeTextId: String, onClickListener: CustomAlertDialog.CustomDialogInterface.OnClickListener)
    fun showConfirmAlertDialog(title: String, imageView: String, message: String, onClickListener: CustomAlertDialog.CustomDialogInterface.OnClickListener)
    fun showConfirmAlertDialog(titleResId: Int, imageView: Int, messageResId: Int, onClickListener: CustomAlertDialog.CustomDialogInterface.OnClickListener)

}