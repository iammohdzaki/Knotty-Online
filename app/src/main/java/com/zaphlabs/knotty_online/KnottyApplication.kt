package com.zaphlabs.knotty_online

import android.app.Application
import android.content.Context
import com.zaphlabs.knotty_online.data.DataManager
import com.zaphlabs.knotty_online.data.firebase.AccountSource
import com.zaphlabs.knotty_online.data.firebase.FirebaseSource
import com.zaphlabs.knotty_online.ui.auth.AuthViewModelFactory
import com.zaphlabs.knotty_online.ui.home.HomeViewModelFactory
import io.paperdb.Paper
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import java.lang.ref.WeakReference

class KnottyApplication : Application() , KodeinAware{

    override val kodein: Kodein
        get() = Kodein.lazy {
            import(androidXModule(this@KnottyApplication))

            bind() from singleton {FirebaseSource()}
            bind() from singleton { DataManager(instance()) }
            bind() from provider { AuthViewModelFactory(instance()) }
            bind() from provider { HomeViewModelFactory(instance()) }

        }

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