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
        id: Long,
        page: Int,
        pageSize: Int
    ): Flow<List<MessagesEntity>> = messagesDao.getUserMessageLastByRecvUserId(id, page, pageSize)

    override suspend fun insertItem(t: MessagesEntity) = messagesDao.insert(t)

    override suspend fun deleteItem(t: MessagesEntity) = messagesDao.delete(t)

    override suspend fun updateItem(t: MessagesEntity) = messagesDao.update(t)
    override fun getDao(): BaseDao<MessagesEntity>  = messagesDao
}