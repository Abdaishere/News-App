package com.ntgclarity.authenticator.api

import com.example.example.NewsClass
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsApi {
    @GET("v2/top-headlines")
    fun top(
        @Query("category") category: String,
        @Query("country") country: String,
        @Query("apikey") apiKey: String
    ): Call<NewsClass?>?
}

var retrofit: Retrofit = Retrofit.Builder()
    .baseUrl("https://newsapi.org/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

var service: NewsApi = retrofit.create(NewsApi::class.java)