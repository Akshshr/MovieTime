package com.kotlinplay.app.base

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.kotlinplay.api.API
import com.kotlinplay.app.KotlinPlayApplication
import com.kotlinplay.app.storage.AppPreferences
import com.kotlinplay.app.util.showToast
import rx.Observable
import rx.subjects.BehaviorSubject

open class BaseActivity : AppCompatActivity() {

    private val lifecycleSubject = BehaviorSubject.create<ActivityEvent>()

    companion object {
        private var TAG = BaseActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleSubject.onNext(ActivityEvent.CREATE)
    }

    enum class ActivityEvent {
        CREATE, DESTROY,
        START, STOP
    }

    fun getApi(): API {
        return (application as KotlinPlayApplication).getApiObject()
    }

    fun getAppPreferences(): AppPreferences {
        return (application as KotlinPlayApplication).getAppPreferencesObject()
    }

    override fun onStart() {
        super.onStart()
        lifecycleSubject.onNext(ActivityEvent.START)
    }

    override fun onStop() {
        lifecycleSubject.onNext(ActivityEvent.STOP)
        super.onStop()
    }

    override fun onDestroy() {
        lifecycleSubject.onNext(ActivityEvent.DESTROY)
        super.onDestroy()
    }

    fun getLifecycleEvents(event: ActivityEvent): Observable<ActivityEvent> {
        return lifecycleSubject.filter { activityEvent: ActivityEvent -> activityEvent == event }
    }

    fun handleError(throwable: Throwable?) {
        Log.e(TAG, "An error occured and handled by genering error message", throwable)
        showError(throwable)
    }

    private fun showError(throwable: Throwable?) {
        runOnUiThread { showToast(throwable?.localizedMessage!!) }
    }


}
