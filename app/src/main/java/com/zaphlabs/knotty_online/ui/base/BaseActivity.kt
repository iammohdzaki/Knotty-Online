package com.zaphlabs.knotty_online.ui.base

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.zaphlabs.knotty_online.R
import com.zaphlabs.knotty_online.ui.customView.CustomAlertDialog
import com.zaphlabs.knotty_online.ui.customView.MaterialEditText
import com.zaphlabs.knotty_online.ui.customView.ProgressDialog
import com.zaphlabs.knotty_online.utils.NetworkUtils
import com.zaphlabs.knotty_online.utils.STATUS_CODES.Companion.FAILED
import com.zaphlabs.knotty_online.utils.STATUS_CODES.Companion.SUCCESS
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper


abstract class BaseActivity : AppCompatActivity(),BaseNavigator {

    private var alertDialogBuilder: AlertDialog.Builder? = null
    private var customAlertDialogBuilder: CustomAlertDialog.Builder? = null
    private var dialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        alertDialogBuilder = AlertDialog.Builder(this)
        customAlertDialogBuilder = CustomAlertDialog.Builder(this)
    }

    override fun setOnClickListeners(onClickListener: View.OnClickListener, vararg views: View) {
        for (view in views)
            view.setOnClickListener(onClickListener)
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }


    override fun isNetworkConnected() : Boolean{
        return NetworkUtils.isNetworkConnected(this)
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

    override fun showProgress() {
        ProgressDialog.show(this@BaseActivity)
    }

    override fun hideProgress() {
        ProgressDialog.dismiss()
    }

    override fun showSnackbar(messageResId: Int, barStatus: Int) {
        showSnackbar(getString(messageResId),barStatus)
    }

    override fun showSnackbar(message: String, barStatus: Int) {
        val restoreBar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val view = restoreBar.view
        when {
            SUCCESS == barStatus -> view.setBackgroundColor(ContextCompat.getColor(this@BaseActivity, R.color.success))
            FAILED == barStatus -> view.setBackgroundColor(ContextCompat.getColor(this@BaseActivity, R.color.red))
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

    override fun showConfirmAlertDialog(title: String, imageView: String, message: String, onClickListener: CustomAlertDialog.CustomDialogInterface.OnClickListener) {
        showConfirmAlertDialog(title, imageView, message, getString(R.string.text_ok), getString(R.string.text_cancel), onClickListener)
    }

    override fun showConfirmAlertDialog(titleResId: Int, imageView: Int, messageResId: Int, onClickListener: CustomAlertDialog.CustomDialogInterface.OnClickListener) {
        showConfirmAlertDialog(getString(titleResId), getString(imageView), getString(messageResId), onClickListener)
    }

    override fun showConfirmAlertDialog(titleResId: String, optionaImage: String, messageResId: String, positiveTextId: String, negativeTextId: String, onClickListener: CustomAlertDialog.CustomDialogInterface.OnClickListener) {
        dialog?.let {
            if (dialog!!.isShowing) {
                dialog!!.dismiss()
            }
        }
        dialog = customAlertDialogBuilder!!.setMessage(messageResId)
            .setTitle(titleResId)
            .setOptionalImage(optionaImage)
            .setPositiveButton(positiveTextId, onClickListener)
            .setNegativeButton(negativeTextId, null)
            .show()
    }
}