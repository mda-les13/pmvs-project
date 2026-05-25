package com.example.weatherapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.WeatherRepository
import com.example.weatherapp.data.WeatherResponse
import com.example.weatherapp.network.WeatherNetwork
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class WeatherUiState {
    object Idle : WeatherUiState()
    object Loading : WeatherUiState()
    data class Success(val data: WeatherResponse) : WeatherUiState()
    data class Error(val message: String) : WeatherUiState()
}

open class MainViewModel : ViewModel() {

    // Создаём репозиторий внутри ViewModel
    private val api = WeatherNetwork.retrofit.create(WeatherNetwork.WeatherApi::class.java)
    open val repository = WeatherRepository(api)

    private val _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Idle)
    val uiState: StateFlow<WeatherUiState> = _uiState

    open fun fetchWeather(lat: Double, lon: Double) {
        viewModelScope.launch {
            _uiState.value = WeatherUiState.Loading
            val result = repository.fetchWeather(lat, lon)
            _uiState.value = result.fold(
                onSuccess = { WeatherUiState.Success(it) },
                onFailure = { WeatherUiState.Error(it.localizedMessage ?: "Network error") }
            )
        }
    }
}