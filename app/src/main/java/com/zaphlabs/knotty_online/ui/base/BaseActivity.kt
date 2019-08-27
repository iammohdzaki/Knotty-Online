package com.zaphlabs.knotty_online.ui.base

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.zaphlabs.knotty_online.R
import com.zaphlabs.knotty_online.ui.customView.MaterialEditText
import com.zaphlabs.knotty_online.utils.NetworkUtils
import com.zaphlabs.knotty_online.utils.STATUS_CODES.Companion.FAILED
import com.zaphlabs.knotty_online.utils.STATUS_CODES.Companion.SUCCESS
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper


abstract class BaseActivity : AppCompatActivity(),BaseNavigator {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }


    override fun isNetworkConnected() {
        NetworkUtils.isNetworkConnected(this)
    }

    override fun onBackPress() {
        super.onBackPressed()
    }

    override fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun showSnackbar(messageResId: Int, barStatus: Int) {
        showSnackbar(getString(messageResId),barStatus)
    }

    override fun showSnackbar(message: String, snackbarStatus: Int) {
        val restoreBar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val view = restoreBar.view
        when {
            SUCCESS == snackbarStatus -> view.setBackgroundColor(ContextCompat.getColor(this@BaseActivity, R.color.success))
            FAILED == snackbarStatus -> view.setBackgroundColor(ContextCompat.getColor(this@BaseActivity, R.color.red))
            else -> view.setBackgroundColor(ContextCompat.getColor(this@BaseActivity, R.color.colorAccent))
        }
        restoreBar.show()
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun showToast(messageResId: Int) {
        showToast(getString(messageResId))
    }

    override fun showErrorOnEditText(editText: MaterialEditText, errorMessage: String) {
        editText.setSelection(editText.text.toString().length)
        editText.isHovered = true
        editText.requestFocus()
        editText.error = errorMessage
    }

    override fun showErrorOnEditText(editText: MaterialEditText, errorMessageResId: Int) {
        showErrorOnEditText(editText, getString(errorMessageResId))
    }

}