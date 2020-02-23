package com.kotlinplay.api.service

import com.kotlinplay.api.APIServiceFactory
import com.kotlinplay.api.model.response.Cast
import com.kotlinplay.api.model.response.SearchTermResponse
import com.kotlinplay.api.model.response.Season
import com.kotlinplay.api.model.response.Show
import rx.Observable

class DataApiService(apiServiceFactory: APIServiceFactory) {


    private var dataApiEndpoint: DataApiEndpoint

    init {
        dataApiEndpoint = apiServiceFactory.createApiService(DataApiEndpoint::class.java)
    }


    fun getSearchTerm(searchTerm: String): Observable<ArrayList<SearchTermResponse>> {
        return dataApiEndpoint.getSearchTerm(searchTerm)
    }

    fun getShows(): Observable<ArrayList<Show>> {
        return dataApiEndpoint.getShows()
    }

    fun getCastForShowId(id:String): Observable<ArrayList<Cast>> {
        return dataApiEndpoint.getCastForId(id)
    }

    fun getSeasonsForShowId(id:String): Observable<ArrayList<Season>> {
        return dataApiEndpoint.getSeasonsForShowId(id)
    }



}
