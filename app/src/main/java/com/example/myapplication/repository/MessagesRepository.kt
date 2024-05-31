package com.example.myapplication.repository

import com.example.myapplication.dao.MessagesDao
import com.example.myapplication.entity.MessagesEntity
import kotlinx.coroutines.flow.Flow

interface MessagesRepository : OfflineRepository<MessagesEntity, MessagesDao> {

    fun getUserMessagesByRecvUserId(id: Long, page: Int, pageSize: Int): Flow<List<MessagesEntity>>

    fun getUserMessagesBySendUserId(id: Long, page: Int, pageSize: Int): Flow<List<MessagesEntity>>

    fun getUserMessageLastByRecvUserId(
        sendUserId: Long,
        recvUserId: Long
    ): Flow<List<MessagesEntity>>

    /**
     * @param [sendUserId]
     * @param [recvUserId]
     * @param [page]
     * @param [pageSize]
     * @return [List<MessagesEntity>]
     */
    fun getMessagesSendAndRecvByUser(
        sendUserId: Long, recvUserId: Long,
        page: Int,
        pageSize: Int
    ): List<MessagesEntity>

    fun getMessagesSendAndRecvFlowByUser(
        sendUserId: Long, recvUserId: Long,
        page: Int,
        pageSize: Int
    ): Flow<List<MessagesEntity>>
}