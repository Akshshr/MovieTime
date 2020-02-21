package com.kotlinplay.app.main.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kotlinplay.app.KotlinPlayApplication
import com.kotlinplay.app.base.BaseActivity
import java.util.*

class MainActivityViewModel : ViewModel() {

    private val mExchangeData = MutableLiveData<Objects>()
    private lateinit var baseActivity: BaseActivity

    fun MainActivityViewModel() {
        baseActivity = KotlinPlayApplication.applicationContext() as BaseActivity
    }



}