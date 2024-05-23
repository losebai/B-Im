package com.example.myapplication.service

import com.example.myapplication.BuildConfig
import com.example.myapplication.viewmodel.MessagesViewModel
import org.noear.socketd.SocketD
import org.noear.socketd.transport.core.entity.FileEntity
import org.noear.socketd.transport.core.entity.StringEntity
import java.io.File

class MessageService(private val messagesViewModel: MessagesViewModel) {


    val session by lazy {
        SocketD.createClient(BuildConfig.SOCKET_URL)
            .listen(UserListener(messagesViewModel))
            .open();
    }

    fun sendFile(filePath: String) = sendFile(File(filePath))

    fun sendFile(file: File){
        session.send("/dispatch-app/file", FileEntity(file))
    }

    fun sendText(text: String){
        session.send("/dispatch-app/text", StringEntity(text))
    }

    fun sendUserText(userId: Long, text: String){
        session.send("/dispatch-app/user/$userId", StringEntity(text))
    }

    fun sendUserFile(userId: Long, file: File){
        session.send("/dispatch-app/user/$userId", FileEntity(file))
    }


}