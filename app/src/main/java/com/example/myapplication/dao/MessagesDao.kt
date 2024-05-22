package com.example.myapplication.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.myapplication.entity.MessagesEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface MessagesDao: BaseDao<MessagesEntity> {

    // 参数 onConflict 用于告知 Room 在发生冲突时应该执行的操作。OnConflictStrategy.IGNORE 策略会忽略新商品。

    @Query("SELECT * from messages WHERE recvUserId = :id")
    suspend fun getUserMessagesByRecvUserId(id : Long) : Flow<List<MessagesEntity>>

    @Query("SELECT * from messages WHERE sendUserId = :id")
    suspend fun getUserMessagesBySendUserId(id : Long) : Flow<List<MessagesEntity>>



}