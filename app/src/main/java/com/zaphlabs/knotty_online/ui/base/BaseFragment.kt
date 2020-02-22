package com.zaphlabs.knotty_online.ui.base

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.zaphlabs.knotty_online.R
import com.zaphlabs.knotty_online.ui.customView.CustomAlertDialog
import com.zaphlabs.knotty_online.ui.customView.MaterialEditText
import com.zaphlabs.knotty_online.ui.customView.ProgressDialog
import com.zaphlabs.knotty_online.utils.NetworkChangeBroadcastReceiver
import com.zaphlabs.knotty_online.utils.NetworkUtils
import com.zaphlabs.knotty_online.utils.STATUS_CODES

abstract class BaseFragment : Fragment(),BaseNavigator {

    private lateinit var mActivity: BaseActivity
    private var alertDialogBuilder: AlertDialog.Builder? = null
    private var customAlertDialogBuilder: CustomAlertDialog.Builder? = null
    private var dialog: Dialog? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as BaseActivity
    }

    override fun onBackPress() {
    }

    override fun isNetworkConnected() : Boolean{
        return NetworkUtils.isNetworkConnected(mActivity)
    }

    override fun setOnClickListeners(onClickListener: View.OnClickListener, vararg views: View) {
        for (view in views)
            view.setOnClickListener(onClickListener)
    }

    override fun hideKeyboard() {
        val view = mActivity.currentFocus
        if (view != null) {
            val inputMethodManager = mActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun showProgress() {
        ProgressDialog.show(mActivity)
    }

    override fun hideProgress() {
        ProgressDialog.dismiss()
    }

    override fun showSnackbar(messageResId: Int, barStatus: Int) {
        showSnackbar(getString(messageResId),barStatus)
    }

    override fun showSnackbar(message: String, barStatus: Int) {
        val restoreBar = Snackbar.make(mActivity.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val view = restoreBar.view
        when {
            STATUS_CODES.SUCCESS == barStatus -> view.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.success))
            STATUS_CODES.FAILED == barStatus -> view.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.red))
            else -> view.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.colorAccent))
        }
        restoreBar.show()
    }

    override fun showToast(message: String) {
        Toast.makeText(mActivity, message, Toast.LENGTH_LONG).show()
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