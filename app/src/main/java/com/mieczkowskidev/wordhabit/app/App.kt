package com.mieczkowskidev.wordhabit.app

import android.app.Application
import android.util.Log
import com.mieczkowskidev.wordhabit.model.TranslateType
import com.mieczkowskidev.wordhabit.utils.SharedPrefsManager

/**
 * Created by Patryk Mieczkowski on 05.04.2018
 */
val prefs: SharedPrefsManager by lazy {
    App.prefs!!
}

class App : Application() {

    lateinit var appComponent: AppComponent

    companion object {
        private val TAG = App::class.java.simpleName
        var state: TranslateType? = null
        var prefs: SharedPrefsManager? = null
    }

    override fun onCreate() {
        super.onCreate()

        initDagger()
        prefs = SharedPrefsManager(this)

        Log.d(TAG, "Creating app 4")
    }

    private fun initDagger() {
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

}