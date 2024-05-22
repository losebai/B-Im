package com.example.myapplication.repository

import com.example.myapplication.entity.MessagesEntity
import kotlinx.coroutines.flow.Flow

interface MessagesRepository  : BaseRepository<MessagesEntity> {

    suspend fun getUserMessagesByRecvUserId(id : Long) : Flow<List<MessagesEntity>>

    suspend fun getUserMessagesBySendUserId(id : Long) : Flow<List<MessagesEntity>>

}