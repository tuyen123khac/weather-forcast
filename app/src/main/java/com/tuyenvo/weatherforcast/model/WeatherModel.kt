package com.tuyenvo.weatherforcast.model

data class WeatherModel(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<DateInfo>,
    val message: Double
)