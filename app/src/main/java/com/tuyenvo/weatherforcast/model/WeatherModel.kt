package com.tuyenvo.weatherforcast.model

data class WeatherModel(
    val city: City,
    val list: List<DateInfo>
)