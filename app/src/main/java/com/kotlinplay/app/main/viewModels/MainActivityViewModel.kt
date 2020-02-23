package com.kotlinplay.app.main.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kotlinplay.api.model.response.SearchTermResponse
import com.kotlinplay.api.model.response.Show
import com.kotlinplay.app.KotlinPlayApplication
import com.kotlinplay.app.main.MainActivity
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*

class MainActivityViewModel : ViewModel() {

    val showsMLiveData = MutableLiveData<ArrayList<Show>>()
    val searchTermMLiveData = MutableLiveData<ArrayList<SearchTermResponse>>()
    val isLoading = MutableLiveData<Boolean>()
    val containsError = MutableLiveData<Throwable>()

    val application: KotlinPlayApplication by lazy {  KotlinPlayApplication.instance!! }

    fun getShows() {
        application.getApiObject().getDataService()
            .getShows()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(({
                this.onShowResponse(it) }), {
                throwable -> onError(throwable)
            })
    }

    private fun onError(throwable: Throwable?) {
        containsError.postValue(throwable)
    }


    fun searchQuery(searchQuery: String) {
        MainActivity.displayingShows = false
        application.getApiObject().getDataService()
            .getSearchTerm(searchQuery)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe( {
                onSearchResponse(it)}, {
                    throwable -> onError(throwable)
            })
    }


    private fun onSearchResponse(it: ArrayList<SearchTermResponse>) {
        isLoading.postValue(false)
        searchTermMLiveData.postValue(it)
    }


    private fun onShowResponse(it: ArrayList<Show>) {
        isLoading.postValue(false)
        showsMLiveData.postValue(it)
    }



}