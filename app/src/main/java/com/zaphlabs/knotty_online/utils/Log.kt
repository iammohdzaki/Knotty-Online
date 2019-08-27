package com.zaphlabs.knotty_online.utils

object Log {

    fun d(s: String, vararg objects: Any) {
        Log.d(s, objects.toString())
    }

    fun e(s: String, vararg objects: Any) {
        Log.e(s, objects.toString())
    }

    fun i(s: String, vararg objects: Any) {
        Log.i(s, objects.toString())
    }

    fun w(s: String, vararg objects: Any) {
        Log.w(s, objects.toString())
    }

}
