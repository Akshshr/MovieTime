package com.kotlinplay.app

import android.app.Application
import com.kotlinplay.BuildConfig
import com.kotlinplay.api.API
import com.kotlinplay.app.storage.AppPreferences

class KotlinPlayApplication : Application() {

    private val api by lazy { API(BuildConfig.API_HOST) }
    private val appPreferences by lazy { AppPreferences(this) }

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        initThirdPartyLib()
    }

    private fun initThirdPartyLib() {
        //Implement 3rd party libs here
    }

    fun getApiObject(): API {
        return api
    }

    fun getAppPreferencesObject(): AppPreferences{
        return appPreferences
    }


    companion object {
        var instance: KotlinPlayApplication? = null

    }


}