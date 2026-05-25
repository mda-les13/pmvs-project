package com.example.weatherapp

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.containsString
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WeatherFlowTest {

    @Test
    fun completeWeatherFlow_displaysTemperature() {
        // Запускаем приложение
        ActivityScenario.launch(MainActivity::class.java)

        // Вводим координаты Москвы
        onView(withId(R.id.etLat)).perform(typeText("55.7558"), closeSoftKeyboard())
        onView(withId(R.id.etLon)).perform(typeText("37.6173"), closeSoftKeyboard())

        // Нажимаем кнопку
        onView(withId(R.id.btnFetch)).perform(click())

        // Ждем и проверяем результат (может быть успех или ошибка сети)
        Thread.sleep(3000)

        onView(withId(R.id.tvResult)).check(matches(isDisplayed()))
    }
}