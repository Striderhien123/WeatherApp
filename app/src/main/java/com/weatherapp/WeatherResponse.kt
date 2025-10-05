package com.weatherapp

import com.squareup.moshi.Json

// 1. The Main container class
data class WeatherResponse(
    // The "weather" field is correct (List<Weather>)
    @field:Json(name = "weather")
    val weather: List<Weather>,

    // The "main" object contains temperature, pressure, etc.
    @field:Json(name = "main")
    val main: Main,

    // The "name" field is correct (city name)
    @field:Json(name = "name")
    val name: String
)

// 2. Data class for the main weather metrics (temperature, humidity, etc.)
data class Main(
    @field:Json(name = "temp")
    val temp: Double,
    @field:Json(name = "pressure")
    val pressure: Int,
    @field:Json(name = "humidity")
    val humidity: Int,
    @field:Json(name = "temp_min")
    val temp_min: Double,
    @field:Json(name = "temp_max")
    val temp_max: Double
)

// 3. Data class for the weather description/icon
data class Weather(
    @field:Json(name = "main")
    val main: String,
    @field:Json(name = "description")
    val description: String,
    @field:Json(name = "icon")
    val icon: String
)