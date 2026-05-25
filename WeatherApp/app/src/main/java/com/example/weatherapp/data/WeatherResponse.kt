package com.example.weatherapp.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

// @JsonClass(generateAdapter = false) явно отключает kapt/ksp генерацию.
// Moshi будет использовать KotlinJsonAdapterFactory (рефлексия), что разрешено условием "без kapt".
@JsonClass(generateAdapter = false)
data class WeatherResponse(
    @Json(name = "latitude") val latitude: Double,
    @Json(name = "longitude") val longitude: Double,
    @Json(name = "current_weather") val currentWeather: CurrentWeather
)

@JsonClass(generateAdapter = false)
data class CurrentWeather(
    @Json(name = "temperature") val temperature: Double,
    @Json(name = "windspeed") val windSpeed: Double,
    @Json(name = "weathercode") val weatherCode: Int,
    @Json(name = "is_day") val isDay: Int
)