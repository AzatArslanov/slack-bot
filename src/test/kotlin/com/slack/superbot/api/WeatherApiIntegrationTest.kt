package com.slack.superbot.api

import com.slack.superbot.config.ApiConfig
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner


@RunWith(SpringRunner::class)
@SpringBootTest(classes = [ApiConfig::class])
internal class WeatherApiIntegrationTest {

    @Autowired
    lateinit var weatherApi: WeatherApi


    @Test
    fun getSaintPetersburgCurrentWeather() {
        val saintPetersburgCurrentWeather = weatherApi.getSaintPetersburgCurrentWeather()
        Assert.assertTrue(saintPetersburgCurrentWeather.matches(Regex(".*\\d+.*")))
    }
}