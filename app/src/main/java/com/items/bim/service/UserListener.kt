package com.items.bim.service

import com.items.bim.common.consts.AppEventConst
import com.items.bim.common.consts.SystemApp
import com.items.bim.common.consts.UserStatus
import com.items.bim.entity.MessagesEntity
import com.items.bim.viewmodel.MessagesViewModel
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.noear.snack.ONode
import org.noear.socketd.transport.core.Entity
import org.noear.socketd.transport.core.Message
import org.noear.socketd.transport.core.Session
import org.noear.socketd.transport.core.entity.StringEntity
import org.noear.socketd.transport.core.listener.EventListener

class UserListener(private val messagesViewModel: MessagesViewModel) : EventListener() {

    private val logger = KotlinLogging.logger {}

    private val scope = CoroutineScope(Dispatchers.Default)

    override fun onOpen(session: Session?) {
        if (session?.isValid == true) {
            session.send(
                "/dispatch-app/open",
                StringEntity("open")
                    .metaPut("time", System.currentTimeMillis().toString())
                    .metaPut("onLine", UserStatus.OFF_LINE.name)
            )
            SystemApp.userStatus = UserStatus.ON_LINE
        }
        logger.info { "onOpen${SystemApp.userStatus}" }
    }

    override fun onMessage(session: Session?, message: Message?) {
        if (message != null) {
            if (message.event() == AppEventConst.USER_MESSAGE) {
                val entity: Entity = message.entity()
                val data = ONode.load(entity.dataAsString())
                val messageData = data.get("messageData").toString()
                val ack = data.get("ack").toString().toInt()
                val userMessage = MessagesEntity(
                    data.get("messagesId").toString().replace("\"",""),
                    data.get("sendUserId").toString().toLong(),
                    data.get("recvUserId").toString().toLong(),
                    messageData.substring(1, messageData.length - 1),
                    data.get("sendDateTime").toString().toLong(),
                    null,
                    if (ack == 0) 1 else ack
                )
                scope.launch {
                    logger.info { "message:${data.toJson()}" }
                    messagesViewModel.saveItem(userMessage)
                    session?.send(AppEventConst.USER_MESSAGE, StringEntity(data.toJson()))
                }
            }
            logger.info { "onMessage::${message}" }
        }
        super.onMessage(session, message)
    }

    override fun onClose(session: Session?) {
        SystemApp.userStatus = UserStatus.OFF_LINE
        logger.info { "onClose" }
        super.onClose(session)
    }

    override fun onError(session: Session?, error: Throwable?) {
    }
}