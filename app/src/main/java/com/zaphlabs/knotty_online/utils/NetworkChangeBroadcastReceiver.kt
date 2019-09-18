package com.zaphlabs.knotty_online.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager

class NetworkChangeBroadcastReceiver : BroadcastReceiver() {

    lateinit var mOnNetworkChangeListener: OnNetworkChangeListener

    fun setOnNetworkChangeListener(onNetworkChangeListener: OnNetworkChangeListener) {
        mOnNetworkChangeListener = onNetworkChangeListener
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION == intent.action)
            if (NetworkUtils.isNetworkConnected(context)) {
                mOnNetworkChangeListener.onConnectionAvailable()
            } else {
                mOnNetworkChangeListener.onConnectionNotAvailable()
            }
    }

    interface OnNetworkChangeListener {

        fun onConnectionAvailable()

        fun onConnectionNotAvailable()
    }

}
