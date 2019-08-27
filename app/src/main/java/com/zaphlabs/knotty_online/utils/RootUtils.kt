package com.zaphlabs.knotty_online.utils

import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

object RootUtil {


    fun isDeviceRooted(): Boolean = hasTestKeys() || isSuperUser() || isSuperUserInstalled()

    private fun hasTestKeys(): Boolean {
        val buildTags = android.os.Build.TAGS
        return buildTags.contains("test-keys")
    }

    private fun isSuperUserInstalled(): Boolean {
        val paths = arrayOf(
            "/system/app/Superuser.apk", "/sbin/su", "/system/bin/su", "/system/xbin/su",
            "/data/local/xbin/su", "/data/local/bin/su", "/system/sd/xbin/su",
            "/system/bin/failsafe/su", "/data/local/su"
        )

        return paths.any { File(it).exists() }
    }


    private fun isSuperUser(): Boolean {
        var process: Process? = null

        return try {
            process = Runtime.getRuntime().exec(arrayOf("/system/xbin/which", "su"))
            val `in`: BufferedReader = BufferedReader(InputStreamReader(process.inputStream))
            `in`.readLine() != null
        } catch (t: Throwable) {
            false
        } finally {
            process?.destroy()
        }
    }
}
