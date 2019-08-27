package com.zaphlabs.knotty_online.utils

import android.content.Context
import android.net.ConnectivityManager

object NetworkUtils {


    fun isNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        cm.let {
            val activeNetwork = it.activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnected
        }

    }
}
