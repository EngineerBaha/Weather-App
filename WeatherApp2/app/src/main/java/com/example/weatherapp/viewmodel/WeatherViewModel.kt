package com.example.weatherapp.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.model.Weather
import com.example.weatherapp.service.WeatherAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class WeatherViewModel : ViewModel() {


    private val weatherService = Retrofit.Builder()
        .baseUrl("http://api.weatherstack.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(WeatherAPI::class.java)

    private val weatherData = MutableLiveData<Weather>()
    val weather: LiveData<Weather> = weatherData

    @SuppressLint("CheckResult")
    fun getCurrentWeather(location: String) {
        weatherService.getCurrentWeather(location, "83aef09022721bbc0313ecd0310544c4")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    weatherData.postValue(response)

                },
                { error -> Log.e("WeatherViewModel", "Error getting weather", error) }
            )
    }
}
