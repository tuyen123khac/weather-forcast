package com.tuyenvo.weatherforcast.service

import com.tuyenvo.weatherforcast.model.WeatherModel
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class WeatherApiService {

    //https://api.openweathermap.org/data/2.5/forecast/daily?q=saigon&cnt=7&appid=60c6fbeb4b93ac653c492ba806fc346d

    private val BASE_URL = "https://api.openweathermap.org/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(WeatherApi::class.java)

    fun getDataService(cityName: String): Single<WeatherModel> {
        return api.getData(cityName)
    }

}