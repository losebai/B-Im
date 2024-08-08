package com.items.bim.service

import android.os.Build
import androidx.annotation.RequiresApi
import com.items.bim.common.consts.AppEventConst
import com.items.bim.common.consts.AppUserAck
import com.items.bim.common.consts.SystemApp
import com.items.bim.common.consts.UserStatus
import com.items.bim.dto.AppUserMessage
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
import java.time.LocalDateTime
import java.time.ZoneOffset

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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessage(session: Session?, message: Message?) {
        if (message != null) {
            if (message.event() == AppEventConst.USER_MESSAGE) {
                val readmes: AppUserMessage = ONode.deserialize(message.dataAsString(), AppUserMessage::class.java)
                val messageData = readmes.messageData
                val ack: Int = message.metaAsInt("ack")
                readmes.ack = ack + 1
                val userMessage = MessagesEntity(
                    readmes.messagesId.toString().replace("\"",""),
                    readmes.sendUserId.toString().toLong(),
                    readmes.recvUserId.toString().toLong(),
                    messageData,
                    readmes.sendDateTime.toInstant(ZoneOffset.UTC).toEpochMilli(),
                    null,
                    readmes.ack
                )
                scope.launch {
                    messagesViewModel.saveItem(userMessage)
                    if (ack == AppUserAck.SERVER_ACK.value) {
                        // 已确认
                        readmes.ack = AppUserAck.ACK_OK.value
                        session?.send(AppEventConst.USER_MESSAGE, StringEntity(ONode.load(readmes).toJson()).at(readmes.sendUserId.toString()))
                    }
                }
                logger.info { "onMessage::${userMessage}" }
            }
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