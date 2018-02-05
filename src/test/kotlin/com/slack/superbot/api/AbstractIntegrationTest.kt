package com.slack.superbot.api

import com.slack.superbot.config.ApiConfig
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [ApiConfig::class])
abstract class AbstractIntegrationTest