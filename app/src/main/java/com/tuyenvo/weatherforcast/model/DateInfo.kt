package com.tuyenvo.weatherforcast.model

import com.google.gson.annotations.SerializedName

data class DateInfo(
    val dt: Long,
    val temp: Temp,
    val pressure: Int,
    val humidity: Int,
    val weather: List<Weather>
)