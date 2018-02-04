package com.slack.superbot.service

import com.nhaarman.mockito_kotlin.*
import com.slack.superbot.core.BotCore
import com.ullink.slack.simpleslackapi.SlackSession
import com.ullink.slack.simpleslackapi.SlackUser
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted
import org.junit.Test


internal class SlackServiceTest {

    @Test
    fun onEvent() {
        val id = "id"
        val realUserId = "realUserId"
        val bot = mock<SlackUser> {
            on { getId() } doReturn id
        }
        val realUser = mock<SlackUser> {
            on { getId() } doReturn realUserId
        }
        val slackSession = mock<SlackSession> {
            on { sessionPersona() } doReturn bot
        }
        val botCore = mock<BotCore> {

        }
        val slackService = SlackService(slackSession, botCore)
        val messagePosted = SlackMessagePosted("content", null, null, null, null, null)

        messagePosted.user = bot
        slackService.onEvent(messagePosted, slackSession)
        verify(botCore, times(0)).process(any())

        messagePosted.user = realUser
        slackService.onEvent(messagePosted, slackSession)
        verify(botCore, times(1)).process(eq(messagePosted))

    }
}