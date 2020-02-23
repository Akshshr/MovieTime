package com.kotlinplay.app.main.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kotlinplay.R
import com.kotlinplay.api.model.response.Cast
import com.kotlinplay.api.model.response.Season
import com.kotlinplay.app.KotlinPlayApplication
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
        containsError.postValue(throwable)
    }

    private fun doneLoading() {
        isLoading.postValue(false)
    }

    private fun CombinedResult(t1: ArrayList<Cast>?, t2: ArrayList<Season>?) {
        castData.postValue(t1)
        seasonsData.postValue(t2)
    }



}