package com.example.myapplication.service

import com.example.myapplication.BuildConfig
import com.example.myapplication.common.consts.AppEventConst
import com.example.myapplication.common.consts.SystemApp
import com.example.myapplication.entity.MessagesEntity
import com.example.myapplication.viewmodel.MessagesViewModel
import io.github.oshai.kotlinlogging.KotlinLogging
import okhttp3.internal.closeQuietly
import org.noear.snack.ONode
import org.noear.socketd.SocketD
import org.noear.socketd.transport.core.entity.FileEntity
import org.noear.socketd.transport.core.entity.StringEntity
import java.io.File



class MessageService(private val messagesViewModel: MessagesViewModel) {

    private val logger = KotlinLogging.logger {}

    val session =
        SocketD.createClient("${BuildConfig.SOCKET_URL}?@=${SystemApp.UserId}")
            .listen(UserListener(messagesViewModel))
            .open();


    fun sendFile(filePath: String) = sendFile(File(filePath))

    fun sendFile(file: File){
        session.send("/dispatch-app/file", FileEntity(file))
    }

    fun sendText(text: String, userName:String){
        session.send("/dispatch-app/text", StringEntity(text).at(userName))
        logger.info {  "/dispatch-app/text::${text}@${userName}"}
    }

    fun sendMessagesEntity(messagesEntity: MessagesEntity){
        val messageStr = ONode.load(messagesEntity)
        session.send(AppEventConst.USER_MESSAGE, StringEntity(messageStr.toJson()).at(messagesEntity.recvUserId.toString()))
        logger.info {  "${AppEventConst.USER_MESSAGE}::${messagesEntity}"}

    }

    fun sendUserText(userId: Long, text: String){
        session.send("/dispatch-app/user/$userId", StringEntity(text))
    }

    fun sendUserFile(userId: Long, file: File){
        session.send("/dispatch-app/user/$userId", FileEntity(file))
    }


    fun reconnect(){
        session.reconnect()
    }

    fun close(){
        session.close()
    }
}