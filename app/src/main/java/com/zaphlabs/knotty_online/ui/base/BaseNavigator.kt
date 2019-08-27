package com.zaphlabs.knotty_online.ui.base

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

}