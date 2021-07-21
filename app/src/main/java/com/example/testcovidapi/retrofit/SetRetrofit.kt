package com.example.testcovidapi.retrofit

import com.example.testcovidapi.api.CovidInterface

object SetRetrofit {
    private const val BASE_URL = "https://api.covid19api.com/"

    val retrofitService: CovidInterface
        get() = RetrofitClient.getClient(BASE_URL).create(CovidInterface::class.java)
}