package com.slack.superbot.core

import com.ullink.slack.simpleslackapi.SlackPreparedMessage
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class BotCore(@Value("\${jira.url}") val jiraUrl: String) {

    fun process(message: SlackMessagePosted) : List<SlackPreparedMessage> {
        val messageContent = message.messageContent.toLowerCase()
        val result = ArrayList<SlackPreparedMessage>()

        if (messageContent.contains(Phrases.CALL_DEVOPS)) {
            val callDevops = SlackPreparedMessage.Builder()
                    .withMessage(Phrases.CALL_DEVOPS_REPLAY)
                    .withLinkNames(true)
                    .build()
            result.add(callDevops)
        }
        findFunnyReplies(messageContent)
                .union(findJiraLinks(messageContent))
                .forEach {
                    val replay = SlackPreparedMessage.Builder()
                            .withMessage(it)
                            .build()
                    result.add(replay)
                }
        return result
    }

    private fun findJiraLinks(message: String) : MutableList<String> {
        val result = ArrayList<String>()
        Regex("(\\s|^)sierra-\\d+").findAll(message).forEach {
            result.add("$jiraUrl/browse/${it.value.trim().toUpperCase()}")
        }
        return result
    }

    private fun findFunnyReplies(message: String) : MutableList<String> {
        val result = ArrayList<String>()
        when {
            message.contains(Phrases.IT_IS_OKAY) -> result.add(Phrases.IT_IS_OKAY_REPLAY)
            message.contains(Phrases.ALL_IN_FAVOR) -> result.add(Phrases.ALL_IN_FAVOR_REPLAY)
            message.contains(Phrases.SPRINT_ENG) || message.contains(Phrases.SPRINT_RUS) -> result.add(Phrases.SPRINT_REPLAY)
        }
        return result
    }
}