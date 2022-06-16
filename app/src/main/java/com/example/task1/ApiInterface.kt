package com.example.task1

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("/v2//shorten?")
    fun getResponseByUrlQuery(@Query("url") pena: String?): Call<ModelBodyResponse>
}