package com.kotlinplay.app.storage

import android.content.Context
import androidx.preference.PreferenceManager
import com.f2prateek.rx.preferences.RxSharedPreferences

class AppPreferences (context: Context) {

    private val rxSharedPreferences: RxSharedPreferences

    init{
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        rxSharedPreferences = RxSharedPreferences.create(sharedPreferences)
    }


    //   ---PREFERENCES KEYS---  //
    private object Keys {
        internal val TEST_KEY = "test_key"
    }


}