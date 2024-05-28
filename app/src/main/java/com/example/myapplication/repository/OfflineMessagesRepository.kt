package com.example.myapplication.repository

import com.example.myapplication.dao.BaseDao
import com.example.myapplication.dao.MessagesDao
import com.example.myapplication.entity.MessagesEntity
import kotlinx.coroutines.flow.Flow

class OfflineMessagesRepository(private val messagesDao: MessagesDao) : MessagesRepository {

    override suspend fun getUserMessagesByRecvUserId(
        id: Long,
        page: Int,
        pageSize: Int
    ): Flow<List<MessagesEntity>> =
        messagesDao.getUserMessagesByRecvUserId(id, page, pageSize)

    override suspend fun getUserMessagesBySendUserId(
        id: Long,
        page: Int,
        pageSize: Int
    ): Flow<List<MessagesEntity>> =
        messagesDao.getUserMessagesBySendUserId(id, page, pageSize)

    override suspend fun getUserMessageLastByRecvUserId(
        sendUserId: Long, recvUserId : Long,
    ): Flow<List<MessagesEntity>> = messagesDao.getUserMessageLastByUserId(sendUserId, recvUserId)

    override suspend fun getMessagesSendAndRecvByUser(
        sendUserId: Long, recvUserId: Long,
        page: Int,
        pageSize: Int
    ) = messagesDao.getMessagesSendAndRecvByUser(sendUserId, recvUserId, page, pageSize)

    override fun getDao(): BaseDao<MessagesEntity> = messagesDao
}