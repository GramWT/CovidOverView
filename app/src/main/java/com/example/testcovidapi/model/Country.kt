package com.example.testcovidapi.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class Country(val Countries: MutableList<CountryData>)

@Parcelize
data class CountryData(
    @SerializedName("Country")
    val country: String? = null,

    @SerializedName("CountryCode")
    val countryCode: String? = null,

    @SerializedName("NewConfirmed")
    val newConfirmed: Int? = null,

    @SerializedName("TotalConfirmed")
    val totalConfirmed: Int? = null,

    @SerializedName("NewDeaths")
    val newDeaths: Int? = null,

    @SerializedName("TotalDeaths")
    val totalDeaths: Int? = null,

    @SerializedName("NewRecovered")
    val newRecovered: Int? = null,

    @SerializedName("TotalRecovered")
    val totalRecovered: Int? = null,

    @SerializedName("Date")
    val date: String? = null
) : Parcelable