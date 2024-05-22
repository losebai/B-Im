package com.example.myapplication.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class MessagesEntity(

    @PrimaryKey
    var messagesId: String,
    var sendUserId: Long,
    var recvUserId: Long,
    var messageData: String,
    var sendDateTime: String,
    var recvDateTime: String,
    var ack: Int
)


data class MessagesDetail(
    var messagesId: String,
    var sendUserImageUri: String,
    var sendUserName: String,
    var sendUserId: Long,
    var recvUserId: Long,
    var messageData: String,
    var sendDateTime: String,
    var recvDateTime: String,
    var ack: Int
)


fun MessagesEntity.toMessagesDetail(
    sendUserName: String,
    sendUserImageUri: String,
): MessagesDetail =
    MessagesDetail(
        messagesId = messagesId, sendUserId = sendUserId, sendDateTime = sendDateTime,
        recvUserId = recvUserId, messageData = messageData, recvDateTime = recvDateTime,
        ack = ack, sendUserImageUri = sendUserImageUri, sendUserName = sendUserName
    )