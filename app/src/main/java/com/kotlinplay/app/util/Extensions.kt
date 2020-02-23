package com.kotlinplay.app.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.kotlinplay.R


fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}


fun Context.openURL(urls: String?) {
    if(urls==null){
        openURL(URL_404)
        showToast(resources.getString(R.string.generic_error))
        return
    }
    val intents = Intent(Intent.ACTION_VIEW,  Uri.parse(urls))
    this.startActivity(intents)
}


fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}


fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}