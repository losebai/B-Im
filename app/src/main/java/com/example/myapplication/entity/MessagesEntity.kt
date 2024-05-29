package com.example.myapplication.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "messages")
data class MessagesEntity(

    @PrimaryKey
    var messagesId: String = UUID.randomUUID().toString().replace("-", ""),
    var sendUserId: Long,
    var recvUserId: Long,
    var messageData: String,
    var sendDateTime: String,
    var recvDateTime: String?,
    var ack: Int
)


data class UserMessages(
    var messagesId: String,
    var sendUserImageUri: String,
    var sendUserName: String,
    var sendUserId: Long,
    var recvUserId: Long,
    val recvUserName: String,
    val recvUserImageUri: String,
    var messageData: String,
    var sendDateTime: String,
    var recvDateTime: String?,
    var ack: Int
)


fun MessagesEntity.toUserMessages(
    sendUserName: String,
    sendUserImageUri: String,
    recvUserName: String,
    recvUserImageUri: String,
): UserMessages =
    UserMessages(
        messagesId = messagesId, sendUserId = sendUserId, sendDateTime = sendDateTime,
        recvUserId = recvUserId, messageData = messageData, recvDateTime = recvDateTime,
        ack = ack, sendUserImageUri = sendUserImageUri, sendUserName = sendUserName,
        recvUserName=recvUserName,recvUserImageUri=recvUserImageUri
    )

fun UserMessages.toUserEntity(
): MessagesEntity =
    MessagesEntity(
        messagesId = messagesId, sendUserId = sendUserId, sendDateTime = sendDateTime,
        recvUserId = recvUserId, messageData = messageData, recvDateTime = recvDateTime,
        ack = ack
    )