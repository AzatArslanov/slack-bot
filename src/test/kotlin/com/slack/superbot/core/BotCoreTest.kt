package com.slack.superbot.core

import com.ullink.slack.simpleslackapi.events.SlackMessagePosted
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class BotCoreTest {
    val jiraUrl = "jira.url"
    val botCore : BotCore

    init {
        this.botCore = BotCore(jiraUrl)
    }

    @Test
    fun process() {
        var messageContent = Phrases.IT_IS_OKAY
        var messagePosted = SlackMessagePosted(messageContent, null, null, null, null, null)
        var listToSend = botCore.process(messagePosted)

        assertEquals(1, listToSend.size)
        assertEquals(Phrases.IT_IS_OKAY_REPLAY, listToSend[0].message)

        messageContent = "${Phrases.ALL_IN_FAVOR} sierra-22"
        messagePosted = SlackMessagePosted(messageContent, null, null, null, null, null)
        listToSend = botCore.process(messagePosted)
        assertEquals(2, listToSend.size)
        assertEquals(Phrases.ALL_IN_FAVOR_REPLAY, listToSend[0].message)
        assertTrue(listToSend[1].message.contains(jiraUrl))
        assertTrue(listToSend[1].message.contains("SIERRA-22"))
    }
}