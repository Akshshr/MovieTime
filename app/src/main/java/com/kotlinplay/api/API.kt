package com.kotlinplay.api

import com.kotlinplay.api.service.DataApiService

class API(val apiHost: String) {

    private val dataApiService by lazy {
        DataApiService(APIServiceFactory(apiHost))
    }

    fun getDataService(): DataApiService {
        return dataApiService
    }


}