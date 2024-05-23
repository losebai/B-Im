package com.example.myapplication.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.myapplication.entity.MessagesEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface MessagesDao : BaseDao<MessagesEntity> {

    // 参数 onConflict 用于告知 Room 在发生冲突时应该执行的操作。OnConflictStrategy.IGNORE 策略会忽略新商品。

    @Query("SELECT * from messages WHERE recvUserId = :id LIMIT (:page * :pageSize), :pageSize")
    fun getUserMessagesByRecvUserId(id: Long, page: Int, pageSize: Int): Flow<List<MessagesEntity>>

    /**
     * @param [id]
     * @return [Flow<List<MessagesEntity>>]
     */
    @Query("SELECT * from messages WHERE sendUserId = :id LIMIT (:page * :pageSize), :pageSize")
    fun getUserMessagesBySendUserId(id: Long, page: Int, pageSize: Int): Flow<List<MessagesEntity>>


    @Query(
        "SELECT m.* from messages m join (select recvUserId, max(sendDateTime) max_time from messages group by recvUserId)" +
                " m2 on m.recvUserId = m2.recvUserId and m2.max_time = m.sendDateTime where m.recvUserId = :id LIMIT (:page * :pageSize), :pageSize"
    )
    fun getUserMessageLastByRecvUserId(
        id: Long,
        page: Int,
        pageSize: Int
    ): Flow<List<MessagesEntity>>
}