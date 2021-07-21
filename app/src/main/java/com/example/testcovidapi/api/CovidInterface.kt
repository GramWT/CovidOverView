package com.example.testcovidapi.api

import com.example.testcovidapi.model.Country
import retrofit2.Call
import retrofit2.http.GET

interface CovidInterface {
    @GET("summary")
    fun getCaseData(): Call<Country>
}