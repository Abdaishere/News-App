package com.ntgclarity.authenticator.Api

import com.example.example.NewsClass
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsApi {
    @GET("v2/everything")
    // TODO from=2022-07-07 sortBy=publishedAt
    fun everything(@Query("q") q: String, @Query("apiKey") apiKey: String): Call<NewsClass?>?
}

var retrofit = Retrofit.Builder()
    .baseUrl("https://newsapi.org/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

var service: NewsApi = retrofit.create(NewsApi::class.java)