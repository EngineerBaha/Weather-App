package com.example.weatherapp.service

import com.example.weatherapp.model.Weather
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {
    //http://api.weatherstack.com/current
    // ?access_key=83aef09022721bbc0313ecd0310544c4&query=Ankara
    @GET("current")
    fun getCurrentWeather(@Query("query") location: String, @Query("access_key") apiKey: String): Single<Weather>

}