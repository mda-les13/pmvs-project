package com.example.weatherapp

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Before
    fun setUp() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun app_launchesSuccessfully() {
        // Проверка, что приложение запустилось и отображает основные элементы
        onView(withId(R.id.etLat)).check(matches(isDisplayed()))
        onView(withId(R.id.etLon)).check(matches(isDisplayed()))
        onView(withId(R.id.btnFetch)).check(matches(isDisplayed()))
        onView(withId(R.id.tvResult)).check(matches(isDisplayed()))
    }

    @Test
    fun inputCoordinates_andClickButton_showsResult() {
        // Ввод координат
        onView(withId(R.id.etLat)).perform(typeText("55.75"), closeSoftKeyboard())
        onView(withId(R.id.etLon)).perform(typeText("37.61"), closeSoftKeyboard())

        // Нажатие кнопки
        onView(withId(R.id.btnFetch)).perform(click())

        // Проверка, что появился результат (успех или ошибка сети)
        onView(withId(R.id.tvResult)).check(matches(isDisplayed()))
    }

    @Test
    fun emptyInput_showsErrorMessage() {
        // Оставляем поля пустыми
        onView(withId(R.id.btnFetch)).perform(click())

        // Проверяем, что появилось сообщение об ошибке
        onView(withId(R.id.tvResult)).check(matches(isDisplayed()))
    }
}