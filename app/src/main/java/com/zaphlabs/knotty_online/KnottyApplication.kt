package com.zaphlabs.knotty_online

import android.app.Application
import android.content.Context
import io.paperdb.Paper
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import java.lang.ref.WeakReference

class KnottyApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        myApplicationContext = WeakReference(this)

        Paper.init(this)

        //Calligraphy init for fonts
        CalligraphyConfig.initDefault(
            CalligraphyConfig.Builder()
                .setDefaultFontPath("font/Montserrat-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        )
    }
    companion object {
        @JvmStatic
        private lateinit var myApplicationContext: WeakReference<Context>

        @JvmStatic
        fun getMyApplicationContext(): WeakReference<Context> {
            return myApplicationContext
        }
    }
}