package com.kotlinplay.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class APIServiceFactory(apiHost: String) {

    //Get A show
    //http://api.tvmaze.com/shows/1

    //Shows cast
    //http://api.tvmaze.com/shows/1/cast

    //Search with Query
    //http://api.tvmaze.com/search/shows?q=hellow

    //Full schedule
    //http://api.tvmaze.com/schedule/full


    private val TAG = APIServiceFactory::class.java.getSimpleName()

    private var retrofit: Retrofit

    init {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        this.retrofit = Retrofit.Builder()
            .baseUrl(apiHost)
            .client(createClient())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun createClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.addNetworkInterceptor { chain ->
            val response = chain.proceed(chain.request())
            response
        }
        client.addNetworkInterceptor { chain ->
            val builder = chain.request().newBuilder()
            val request = builder.build()
            chain.proceed(request)
        }
        return client.build()
    }

    fun <S> createApiService(serviceClass: Class<S>): S {
        return retrofit.create(serviceClass)
    }

}