package com.slack.superbot.service

import com.slack.superbot.core.BotCore
import com.ullink.slack.simpleslackapi.SlackSession
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted
import com.ullink.slack.simpleslackapi.listeners.SlackMessagePostedListener
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class SlackService(val session: SlackSession, val botCore: BotCore) : SlackMessagePostedListener {
    val log: Log = LogFactory.getLog(SlackService::class.java)

    override fun onEvent(event: SlackMessagePosted, session: SlackSession) {
        log.info("$event")
        if (event.user.id == session.sessionPersona().id) {
            log.info("Skip myself message")
            return
        }
        botCore.process(event).forEach {
            session.sendMessage(event.channel, it)
        }
    }

    @PostConstruct
    fun init() {
        session.addMessagePostedListener(this)
    }
}