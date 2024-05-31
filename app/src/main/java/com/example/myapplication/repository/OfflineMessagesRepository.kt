package com.example.myapplication.repository

import com.example.myapplication.dao.BaseDao
import com.example.myapplication.dao.MessagesDao
import com.example.myapplication.entity.MessagesEntity
import kotlinx.coroutines.flow.Flow

class OfflineMessagesRepository(private val messagesDao: MessagesDao) : MessagesRepository {

    override  fun getUserMessagesByRecvUserId(
        id: Long,
        page: Int,
        pageSize: Int
    ): Flow<List<MessagesEntity>> =
        messagesDao.getUserMessagesByRecvUserId(id, page, pageSize)

    override  fun getUserMessagesBySendUserId(
        id: Long,
        page: Int,
        pageSize: Int
    ): Flow<List<MessagesEntity>> =
        messagesDao.getUserMessagesBySendUserId(id, page, pageSize)

    override  fun getUserMessageLastByRecvUserId(
        sendUserId: Long, recvUserId : Long,
    ): Flow<List<MessagesEntity>> = messagesDao.getUserMessageLastByUserId(sendUserId, recvUserId)

    override  fun getMessagesSendAndRecvByUser(
        sendUserId: Long, recvUserId: Long,
        page: Int,
        pageSize: Int
    ) = messagesDao.getMessagesSendAndRecvByUser(sendUserId, recvUserId, page, pageSize)


    override fun getMessagesSendAndRecvFlowByUser(
        sendUserId: Long,
        recvUserId: Long,
        page: Int,
        pageSize: Int
    ): Flow<List<MessagesEntity>> = messagesDao.getMessagesSendAndRecvFlowByUser(sendUserId,recvUserId,page,pageSize)

    override fun getDao(): BaseDao<MessagesEntity> = messagesDao
}