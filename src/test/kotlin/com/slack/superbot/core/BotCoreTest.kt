package com.slack.superbot.core

import com.nhaarman.mockito_kotlin.*
import com.slack.superbot.api.SierraWebVersion
import com.slack.superbot.api.Weather
import com.ullink.slack.simpleslackapi.SlackPersona
import com.ullink.slack.simpleslackapi.SlackSession
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test


internal class BotCoreTest {
    private val jiraUrl = "jira.url"
    private val slackPersona : SlackPersona = mock {
        on { id } doReturn "id"
    }
    private val session : SlackSession = mock {
        on { sessionPersona() } doReturn slackPersona
    }
    private val sierraWebVersion : SierraWebVersion = mock {}
    private val weather: Weather = mock {}
    private val botCore : BotCore = BotCore(jiraUrl, session, weather, sierraWebVersion)

    @Test
    fun processItIsOkay() {
        val messageContent = Phrases.IT_IS_OKAY
        val messagePosted = SlackMessagePosted(messageContent, null, null, null, null, null)
        val listToSend = botCore.process(messagePosted)

        assertEquals(1, listToSend.size)
        assertEquals(Phrases.IT_IS_OKAY_REPLAY, listToSend[0].message)
    }

    @Test
    fun processAllInFavor() {
        val messageContent = "${Phrases.ALL_IN_FAVOR} sierra-22"
        val messagePosted = SlackMessagePosted(messageContent, null, null, null, null, null)
        val listToSend = botCore.process(messagePosted)

        assertEquals(2, listToSend.size)
        assertEquals(Phrases.ALL_IN_FAVOR_REPLAY, listToSend[0].message)
        assertTrue(listToSend[1].message.contains(jiraUrl))
        assertTrue(listToSend[1].message.contains("SIERRA-22"))
    }

    @Test
    fun processSprint() {
        var messageContent = Phrases.SPRINT_RUS
        var messagePosted = SlackMessagePosted(messageContent, null, null, null, null, null)
        var listToSend = botCore.process(messagePosted)

        assertEquals(1, listToSend.size)
        assertEquals(Phrases.SPRINT_REPLAY, listToSend[0].message)

        messageContent = Phrases.SPRINT_ENG
        messagePosted = SlackMessagePosted(messageContent, null, null, null, null, null)
        listToSend = botCore.process(messagePosted)

        assertEquals(1, listToSend.size)
        assertEquals(Phrases.SPRINT_REPLAY, listToSend[0].message)
    }

    @Test
    fun processSierraWebVersion() {
        val server = "5023"
        val messageContent = "${Phrases.SIERRA_SERVER_INFO} на $server"
        val messagePosted = SlackMessagePosted(messageContent, null, null, null, null, null)
        val listToSend = botCore.process(messagePosted)

        assertEquals(1, listToSend.size)
        verify(sierraWebVersion, times(1)).getVersion(eq(server))
    }
}