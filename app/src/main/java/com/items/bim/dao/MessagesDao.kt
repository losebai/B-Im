package com.items.bim.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.items.bim.common.consts.AppUserAck
import com.items.bim.entity.MessagesEntity
import com.items.bim.entity.NoAckUserMessages
import com.items.bim.entity.UserMessages
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


    /**消息列表中没有确认的消息数量
     *
     * @param [id]
     * @param [page]
     * @param [pageSize]
     * @return [Flow<List<MessagesEntity>>]
     */
    @Query(
        "SELECT m.*,m1.num from messages m join ( " +
                "SELECT sendUserId,recvUserId,max(sendDateTime) max_time, sum(case  when ack == 3 then 1 else 0 end ) num " +
                "from messages where  recvUserId = :recvUserId or sendUserId = :recvUserId group by sendUserId,recvUserId) m1 " +
                "on m1.max_time = m.sendDateTime where  m.recvUserId = :recvUserId or m.sendUserId = :recvUserId"
    )
    fun getUserMessageLastByUserId(recvUserId : Long): Flow<List<UserMessages>>

    /**获取和某个人消息，接收人和发送人
     * 全部消息
     * @param [sendUserId]
     * @param [recvUserId]
     * @param [page]
     * @param [pageSize]
     * @return [Flow<List<MessagesEntity>>]
     */
    @Query("select distinct * from (" +
            "select * from messages where sendUserId  = :sendUserId and recvUserId = :recvUserId  " +
            "union all " +
            "select * from messages where sendUserId  = :recvUserId and recvUserId = :sendUserId " +
            ")" +
            " order by sendDateTime desc LIMIT (:page - 1 * :pageSize), :pageSize")
    fun getMessagesSendAndRecvByUser(sendUserId: Long, recvUserId : Long,
                                     page: Int,
                                     pageSize: Int) : List<MessagesEntity>

    /**
     * 获取和某个人消息，接收人和发送人，未被确认的消息
     * 对方发送的，我没确认（待确认中的）一般在消息沟通中
     * @param [sendUserId]
     * @param [recvUserId]
     * @param [page]
     * @param [pageSize]
     * @return [Flow<List<MessagesEntity>>]
     */
    @Query("select * from messages where sendUserId = :sendUserId and recvUserId = :recvUserId  and ack = 3 LIMIT (:page - 1 * :pageSize), :pageSize")
    fun getMessagesSendAndRecvFlowByUser(sendUserId: Long, recvUserId : Long,
                                     page: Int,
                                     pageSize: Int) : Flow<List<MessagesEntity>>

}