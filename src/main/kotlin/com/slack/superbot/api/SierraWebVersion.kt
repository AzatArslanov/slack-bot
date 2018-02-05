package com.slack.superbot.api

import com.slack.superbot.core.Phrases
import org.jsoup.Jsoup
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

@Component
class SierraWebVersion(@Value("\${sierra.url.pattern}") val urlPattern: String) {
    private val timeout : Int = 2000

    fun getVersion(server: String) : String {
        val url = urlPattern.format(server)
        return try {
            val document = Jsoup.connect(url).timeout(timeout).get()
            document.selectFirst("tr :contains(Compile-Time)")?.parent()?.text() ?: Phrases.VERSION_ERROR
        } catch (ignored: UnknownHostException) {
            Phrases.UNKNOWN_HOST.format(url)
        } catch (ex: Exception) {
            when(ex) {
                is SocketTimeoutException, is SocketException -> Phrases.TIMEOUT_EXCEPTION.format(server, timeout)
                else -> throw ex
            }
        }
    }

}