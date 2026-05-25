package com.example.weatherapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.weatherapp.viewmodel.MainViewModel
import com.example.weatherapp.viewmodel.WeatherUiState
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var etLat: EditText
    private lateinit var etLon: EditText
    private lateinit var btnFetch: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var tvResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Инициализация view
        etLat = findViewById(R.id.etLat)
        etLon = findViewById(R.id.etLon)
        btnFetch = findViewById(R.id.btnFetch)
        progressBar = findViewById(R.id.progressBar)
        tvResult = findViewById(R.id.tvResult)

        // Обработчик кнопки
        btnFetch.setOnClickListener {
            val lat = etLat.text.toString().toDoubleOrNull()
            val lon = etLon.text.toString().toDoubleOrNull()

            if (lat != null && lon != null) {
                viewModel.fetchWeather(lat, lon)
            } else {
                Toast.makeText(this, "Введите корректные координаты", Toast.LENGTH_SHORT).show()
            }
        }

        // Наблюдение за состоянием
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is WeatherUiState.Idle -> {
                        progressBar.visibility = View.GONE
                        tvResult.text = "Введите координаты и нажмите кнопку"
                    }
                    is WeatherUiState.Loading -> {
                        progressBar.visibility = View.VISIBLE
                        tvResult.text = ""
                    }
                    is WeatherUiState.Success -> {
                        progressBar.visibility = View.GONE
                        val temp = state.data.currentWeather.temperature
                        val wind = state.data.currentWeather.windSpeed
                        val code = state.data.currentWeather.weatherCode
                        tvResult.text = "🌡️ Температура: ${temp}°C\n💨 Ветер: ${wind} км/ч\nКод погоды: $code"
                    }
                    is WeatherUiState.Error -> {
                        progressBar.visibility = View.GONE
                        tvResult.text = "❌ Ошибка: ${state.message}"
                    }
                }
            }
        }
    }
}