package com.slack.superbot.core

import com.ullink.slack.simpleslackapi.events.SlackMessagePosted
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class BotCoreTest {
    private val jiraUrl = "jira.url"
    private val botCore : BotCore

    init {
        this.botCore = BotCore(jiraUrl)
    }

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
}