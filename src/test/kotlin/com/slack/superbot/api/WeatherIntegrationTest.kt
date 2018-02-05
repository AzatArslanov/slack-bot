package com.slack.superbot.api

import org.junit.Assert
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired


internal class WeatherIntegrationTest : AbstractIntegrationTest() {

    @Autowired
    lateinit var weather: Weather


    @Test
    fun getSaintPetersburgCurrentWeather() {
        val saintPetersburgCurrentWeather = weather.getSaintPetersburgCurrentWeather()
        Assert.assertTrue(saintPetersburgCurrentWeather.matches(Regex(".*\\d+.*")))
    }
}