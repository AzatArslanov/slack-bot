package com.slack.superbot.core

import com.nhaarman.mockito_kotlin.*
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
    private val weather: Weather = mock {}
    private val botCore : BotCore = BotCore(jiraUrl, session, weather)

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
}