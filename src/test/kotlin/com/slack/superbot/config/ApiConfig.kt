package com.slack.superbot.config

import org.apache.http.client.HttpClient
import org.apache.http.impl.client.HttpClientBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan("com.slack.superbot.api")
class ApiConfig {

    @Bean
    fun httpClient() : HttpClient = HttpClientBuilder.create().build()

}