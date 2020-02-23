package com.kotlinplay.app.main.viewModels

import android.app.Application
import android.util.Log
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kotlinplay.R
import com.kotlinplay.api.model.response.Cast
import com.kotlinplay.api.model.response.SearchTermResponse
import com.kotlinplay.api.model.response.Season
import com.kotlinplay.api.model.response.Show
import com.kotlinplay.app.KotlinPlayApplication
import com.kotlinplay.app.base.BaseActivity
import com.kotlinplay.app.main.MainActivity
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*

class ShowDetailsViewModel : ViewModel() {

    val castData = MutableLiveData<ArrayList<Cast>>()
    val seasonsData = MutableLiveData<ArrayList<Season>>()
    val isLoading = MutableLiveData<Boolean>()
    val containsError = MutableLiveData<Throwable>()

    companion object {
        private var TAG = ShowDetailsViewModel::class.java.simpleName
    }


    private lateinit var baseActivity: BaseActivity
    val application: KotlinPlayApplication by lazy {  KotlinPlayApplication.instance!! }

    fun burstFetchShowDetails(showId: String?) {
        Observable.zip(
            application.getApiObject().getDataService().getCastForShowId(showId!!),
            application.getApiObject().getDataService().getSeasonsForShowId(showId))
          { t1, t2 -> CombinedResult(t1, t2) }
            .subscribeOn(Schedulers.io())
            .onErrorReturn { handleError(throwable = Throwable(application.applicationContext.resources.getString(R.string.cast_seasons_error))) }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnCompleted { doneLoading() }
            .subscribe()
    }

    private fun handleError(throwable: Throwable) {
        //Tell Activity there is was an error
        Log.d(TAG, "Error")
        containsError.postValue(throwable)


    }

    private fun doneLoading() {
        //Tell activity loading is done
        Log.d(TAG, "loading done")
        isLoading.postValue(false)
    }

    private fun CombinedResult(t1: ArrayList<Cast>?, t2: ArrayList<Season>?) {
        castData.postValue(t1)
        seasonsData.postValue(t2)
    }



}