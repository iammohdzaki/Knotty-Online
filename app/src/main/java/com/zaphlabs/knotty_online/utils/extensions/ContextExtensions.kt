package com.zaphlabs.knotty_online.utils.extensions

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

fun Context.hideKeyboard() {
    try {
        val inputManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if ((this as Activity).currentFocus == null || this.currentFocus!!.windowToken == null) {
            return
        }
        inputManager.hideSoftInputFromWindow(this.currentFocus!!.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS)
    } catch (exception: Exception) {
        Log.d("Context Extension","" + exception.localizedMessage)
    }
}

fun Context.convertDpToPixel(dp: Float): Float {
    return dp * (this.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

fun Context.toast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
fun Context.toast(msg: Int) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

fun Context.longToast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
fun Context.longToast(msg: Int) = Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
