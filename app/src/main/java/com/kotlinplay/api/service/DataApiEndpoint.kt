package com.kotlinplay.api.service

import com.kotlinplay.api.model.response.Cast
import com.kotlinplay.api.model.response.SearchTermResponse
import com.kotlinplay.api.model.response.Season
import com.kotlinplay.api.model.response.Show
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rx.Observable

interface DataApiEndpoint {

    //Single Search
    @GET("search/shows?")
     fun getSearchTerm( @Query("q") term: String): Observable<ArrayList<SearchTermResponse>>

    //Get All shows
    @GET("shows")
    fun getShows(): Observable<ArrayList<Show>>

    //Get cast for a particular show
    @GET("shows/{id}/cast")
    fun getCastForId( @Path("id") id: String): Observable<ArrayList<Cast>>

    //Get seasons for a particular show
    @GET("shows/{id}/seasons")
    fun getSeasonsForShowId( @Path("id") id: String): Observable<ArrayList<Season>>



}
