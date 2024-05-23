package com.example.myapplication.service

import com.example.myapplication.common.consts.SystemApp
import com.example.myapplication.common.consts.UserStatus
import com.example.myapplication.entity.MessagesEntity
import com.example.myapplication.viewmodel.MessagesViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.noear.snack.ONode
import org.noear.socketd.transport.core.Entity
import org.noear.socketd.transport.core.Listener
import org.noear.socketd.transport.core.Message
import org.noear.socketd.transport.core.Session
import org.noear.socketd.transport.core.entity.StringEntity
import java.util.Date

class UserListener(private val messagesViewModel: MessagesViewModel): Listener {


    private val scope = CoroutineScope(Dispatchers.Main)

    override fun onOpen(session: Session?) {
        if (session?.isValid == true){
            session.send("/dispatch-app/open",
                StringEntity("open")
                    .metaPut("time", Date().toString())
                    .metaPut("onLine", UserStatus.OFF_LINE.name))
            SystemApp.userStatus = UserStatus.ON_LINE
        }
    }

    override fun onMessage(session: Session?, message: Message?) {
        if (message?.isRequest == true){
            if (message.event() == "/dispatch-app/message/${SystemApp.UserId}"){
                val entity: Entity =  message.entity()
                val data = ONode.load(entity.dataAsString())
                val userMessage : MessagesEntity = data
                    .toObject(MessagesEntity::class.java)
                scope.launch {
                    messagesViewModel.saveItem(userMessage)
                    userMessage.ack = 1
                    data["ack"] = 1
                    session?.replyEnd(message, StringEntity(data.toJson()))
                    messagesViewModel.updateItem(userMessage)
                }
            }
        }
    }

    override fun onClose(session: Session?) {
        if (session?.isValid == true){
            session.send("/dispatch-app/close",
                StringEntity("close")
                    .metaPut("time", Date().toString())
                    .metaPut("offLine", UserStatus.OFF_LINE.name))
            SystemApp.userStatus = UserStatus.OFF_LINE
        }
    }

    override fun onError(session: Session?, error: Throwable?) {
    }
}