package com.kotlinplay.app.main

import android.content.Intent
import android.os.Bundle
import com.kotlinplay.app.base.BaseActivity

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()

    }
}
