package com.example.myapplication.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.entity.MessagesEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface MessagesDao : BaseDao<MessagesEntity> {

    // 参数 onConflict 用于告知 Room 在发生冲突时应该执行的操作。OnConflictStrategy.IGNORE 策略会忽略新商品。

    /**
     *  读取接收人的消息
     * @param [id]
     * @param [page]
     * @param [pageSize]
     * @return [Flow<List<MessagesEntity>>]
     */
    @Query("SELECT * from messages WHERE recvUserId = :id LIMIT (:page - 1 * :pageSize), :pageSize")
    fun getUserMessagesByRecvUserId(id: Long, page: Int, pageSize: Int): Flow<List<MessagesEntity>>

    /**
     * 读取发送人的消息
     * @param [id]
     * @return [Flow<List<MessagesEntity>>]
     */
    @Query("SELECT * from messages WHERE sendUserId = :id LIMIT (:page - 1 * :pageSize), :pageSize")
    fun getUserMessagesBySendUserId(id: Long, page: Int, pageSize: Int): Flow<List<MessagesEntity>>


    /**获取最新的消息，并没有被确认的消息，按人进行分组
     * @param [id]
     * @param [page]
     * @param [pageSize]
     * @return [Flow<List<MessagesEntity>>]
     */
    @Query(
        "SELECT m.* from messages m join ( SELECT sendUserId,recvUserId,max(sendDateTime) max_time from messages GROUP BY sendUserId,recvUserId ) m1 " +
                "on m.recvUserId = m1.recvUserId and m.sendUserId = m1.sendUserId and m1.max_time = m.sendDateTime WHERE m.sendUserId = :sendUserId or m.recvUserId = :recvUserId "
    )
    fun getUserMessageLastByUserId(
        sendUserId: Long, recvUserId : Long,
    ): Flow<List<MessagesEntity>>

    /**获取和某个人消息，接收人和发送人
     * @param [sendUserId]
     * @param [recvUserId]
     * @param [page]
     * @param [pageSize]
     * @return [Flow<List<MessagesEntity>>]
     */
    @Query("select * from (" +
            "select * from messages where sendUserId  = :sendUserId and recvUserId = :recvUserId  " +
            "union all " +
            "select * from messages where sendUserId  = :recvUserId and recvUserId = :sendUserId " +
            ")" +
            " LIMIT (:page - 1 * :pageSize), :pageSize")
    fun getMessagesSendAndRecvByUser(sendUserId: Long, recvUserId : Long,
                                     page: Int,
                                     pageSize: Int) : List<MessagesEntity>

    /**获取和某个人消息，接收人和发送人，未被确认的消息
     * @param [sendUserId]
     * @param [recvUserId]
     * @param [page]
     * @param [pageSize]
     * @return [Flow<List<MessagesEntity>>]
     */
    @Query("select * from messages where sendUserId = :sendUserId and recvUserId = :recvUserId  and ack = 1 LIMIT (:page - 1 * :pageSize), :pageSize")
    fun getMessagesSendAndRecvFlowByUser(sendUserId: Long, recvUserId : Long,
                                     page: Int,
                                     pageSize: Int) : Flow<List<MessagesEntity>>

}