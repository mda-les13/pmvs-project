package com.example.weatherapp.data

import com.example.weatherapp.network.WeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepository(private val api: WeatherNetwork.WeatherApi) {
    suspend fun fetchWeather(lat: Double, lon: Double): Result<WeatherResponse> = withContext(Dispatchers.IO) {
        try {
            val response = api.getWeather(lat, lon)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}