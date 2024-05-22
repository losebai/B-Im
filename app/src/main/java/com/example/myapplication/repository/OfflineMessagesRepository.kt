package com.example.myapplication.repository

import com.example.myapplication.dao.MessagesDao
import com.example.myapplication.entity.MessagesEntity
import kotlinx.coroutines.flow.Flow

class OfflineMessagesRepository(private val messagesDao: MessagesDao) : MessagesRepository {

    override suspend fun getUserMessagesByRecvUserId(id : Long): Flow<List<MessagesEntity>> =
        messagesDao.getUserMessagesByRecvUserId(id)

    override suspend fun getUserMessagesBySendUserId(id : Long): Flow<List<MessagesEntity>> =
        messagesDao.getUserMessagesBySendUserId(id)

    override suspend fun insertItem(t: MessagesEntity) = messagesDao.insert(t)

    override suspend fun deleteItem(t: MessagesEntity) =  messagesDao.delete(t)

    override suspend fun updateItem(t: MessagesEntity) = messagesDao.update(t)
}