package com.example.weatherapp.model


import com.google.gson.annotations.SerializedName

data class Weather(
    @SerializedName("current")
    val current: Current,
    @SerializedName("location")
    val location: Location,
    @SerializedName("request")
    val request: Request
)