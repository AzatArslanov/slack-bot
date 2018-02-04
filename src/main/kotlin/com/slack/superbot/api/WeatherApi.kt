package com.slack.superbot.api

import com.google.gson.JsonParser
import com.slack.superbot.core.Phrases
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpGet
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.InputStream
import java.nio.charset.Charset

fun InputStream.readTextAndClose(charset: Charset = Charsets.UTF_8): String {
    return this.bufferedReader(charset).use { it.readText() }
}

@Component
class WeatherApi(@Value("\${weather.token}") val token: String, val httpClient: HttpClient) {
    private val WEATHER_API_URL = "https://api.apixu.com"

    fun getSaintPetersburgCurrentWeather() : String {
        val request = HttpGet("$WEATHER_API_URL/v1/current.json?key=$token&q=Saint-Petersburg")
        val response = httpClient.execute(request)
        val responseJson = JsonParser().parse(response.entity.content.readTextAndClose()).asJsonObject
        val current = responseJson.get("current")?.asJsonObject?.get("temp_c")?.asString
        val feelsLike = responseJson.get("current")?.asJsonObject?.get("feelslike_c")?.asString
        return Phrases.WEATHER_REPLAY.format(current, feelsLike)
    }

}