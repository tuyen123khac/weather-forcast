package com.tuyenvo.weatherforcast.service

import com.tuyenvo.weatherforcast.model.WeatherModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    //https://api.openweathermap.org/data/2.5/forecast/daily?q=saigon&cnt=7&appid=60c6fbeb4b93ac653c492ba806fc346d

    @GET("data/2.5/forecast/daily?&cnt=7&units=metric&appid=60c6fbeb4b93ac653c492ba806fc346d")
    fun getData(
        @Query("q") cityName: String
    ): Single<WeatherModel>
}