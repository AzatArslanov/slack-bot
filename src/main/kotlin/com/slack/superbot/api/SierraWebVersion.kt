package com.slack.superbot.api

import com.slack.superbot.core.Phrases
import org.apache.http.client.HttpClient
import org.jsoup.Jsoup
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.net.SocketTimeoutException

@Component
class SierraWebVersion(@Value("\${sierra.url.pattern}") val urlPattern: String, val httpClient: HttpClient) {
    private val timeout : Int = 2000

    fun getVersion(server: String) : String {
        return try {
            val document = Jsoup.connect(urlPattern.format(server)).timeout(timeout).get()
            document.selectFirst("tr :contains(Compile-Time)")?.parent()?.text() ?: Phrases.VERSION_ERROR
        } catch (ignored: SocketTimeoutException) {
            Phrases.TIMEOUT_EXCEPTION.format(server, timeout)
        }
    }

}