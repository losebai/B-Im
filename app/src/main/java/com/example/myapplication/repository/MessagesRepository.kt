package com.example.myapplication.repository

import com.example.myapplication.dao.MessagesDao
import com.example.myapplication.entity.MessagesEntity
import kotlinx.coroutines.flow.Flow

interface MessagesRepository  : OfflineRepository<MessagesEntity, MessagesDao> {

    suspend fun getUserMessagesByRecvUserId(id : Long, page: Int, pageSize: Int) : Flow<List<MessagesEntity>>

    suspend fun getUserMessagesBySendUserId(id : Long, page: Int, pageSize: Int) : Flow<List<MessagesEntity>>

    suspend fun getUserMessageLastByRecvUserId(id : Long, page: Int, pageSize: Int) : Flow<List<MessagesEntity>>
}