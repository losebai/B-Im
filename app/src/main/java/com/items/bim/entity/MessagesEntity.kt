package com.items.bim.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.Query
import java.util.UUID

@Entity(tableName = "messages")
data class MessagesEntity(

    @PrimaryKey
    var messagesId: String = UUID.randomUUID().toString().replace("-", ""),
    var sendUserId: Long,
    var recvUserId: Long,
    var messageData: String,

    @ColumnInfo(name = "sendDateTime")
    var sendDateTime: Long,
    var recvDateTime: Long?,
    var ack: Int,
)

data class UserMessages(
    var messagesId: String,
    var sendUserId: Long,
    var recvUserId: Long,
    var messageData: String,
    var ack: Int,
    var sendDateTime: Long = 0,
    var recvDateTime: Long? = 0,
    var num: Int = 0
){
    @Ignore
    var recvUserName: String = "";
    @Ignore
    var recvUserImageUri: String = "";
    @Ignore
    var sendUserImageUri: String = "";
    @Ignore
    var sendUserName: String = ""
}

data class NoAckUserMessages(
    val sendUserId: Long,
    val recvUserId: Long,
    val num: Int
)


fun MessagesEntity.toUserMessages(): UserMessages =
    UserMessages(
        messagesId = messagesId, sendUserId = sendUserId, sendDateTime = sendDateTime,
        recvUserId = recvUserId, messageData = messageData, recvDateTime = recvDateTime,
        ack = ack
    )

fun UserMessages.toUserEntity(
): MessagesEntity =
    MessagesEntity(
        messagesId = messagesId, sendUserId = sendUserId, sendDateTime = sendDateTime,
        recvUserId = recvUserId, messageData = messageData, recvDateTime = recvDateTime,
        ack = ack
    )