package com.example.weatherapp


import com.example.weatherapp.data.CurrentWeather
import com.example.weatherapp.data.WeatherRepository
import com.example.weatherapp.data.WeatherResponse
import com.example.weatherapp.viewmodel.MainViewModel
import com.example.weatherapp.viewmodel.WeatherUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    @Mock
    private lateinit var repository: WeatherRepository

    private lateinit var viewModel: MainViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = MainViewModel()
        // Заменяем репозиторий на мок (в реальном проекте используйте DI)
    }

    @Test
    fun fetchWeather_loading_thenSuccess() = runTest {
        // Arrange
        val testResponse = WeatherResponse(
            latitude = 55.75,
            longitude = 37.61,
            currentWeather = CurrentWeather(
                temperature = 20.5,
                windSpeed = 5.2,
                weatherCode = 0,
                isDay = 1
            )
        )

        `when`(repository.fetchWeather(55.75, 37.61)).thenReturn(Result.success(testResponse))

        // Act
        viewModel.fetchWeather(55.75, 37.61)
        advanceUntilIdle()

        // Assert
        val state = viewModel.uiState.value
        assertTrue(state is WeatherUiState.Success)
        assertEquals(20.5, (state as WeatherUiState.Success).data.currentWeather.temperature, 0.01)
    }

    @Test
    fun fetchWeather_loading_thenError() = runTest {
        // Arrange
        `when`(repository.fetchWeather(55.75, 37.61)).thenReturn(Result.failure(Exception("Network error")))

        // Act
        viewModel.fetchWeather(55.75, 37.61)
        advanceUntilIdle()

        // Assert
        val state = viewModel.uiState.value
        assertTrue(state is WeatherUiState.Error)
    }

    @Test
    fun initial_state_isIdle() {
        // Assert
        assertTrue(viewModel.uiState.value is WeatherUiState.Idle)
    }
}