package com.items.bim.repository.impl

import com.items.bim.dao.BaseDao
import com.items.bim.dao.MessagesDao
import com.items.bim.entity.MessagesEntity
import com.items.bim.entity.UserMessages
import com.items.bim.repository.MessagesRepository
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
    ): Flow<List<UserMessages>> = messagesDao.getUserMessageLastByUserId(sendUserId, recvUserId)

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
    ) = messagesDao.getMessagesSendAndRecvFlowByUser(sendUserId,recvUserId,page,pageSize)

    override fun getDao(): BaseDao<MessagesEntity> = messagesDao


}