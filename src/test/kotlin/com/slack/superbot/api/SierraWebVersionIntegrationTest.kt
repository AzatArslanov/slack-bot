package com.slack.superbot.api

import org.junit.Test

import org.junit.Assert.*
import org.springframework.beans.factory.annotation.Autowired

class SierraWebVersionIntegrationTest : AbstractIntegrationTest() {

    @Autowired
    lateinit var sierraWebVersion: SierraWebVersion

    @Test
    fun getVersion() {
        val version = sierraWebVersion.getVersion("5069")
        assertTrue(version.contains("Compile-Time"))
    }
}