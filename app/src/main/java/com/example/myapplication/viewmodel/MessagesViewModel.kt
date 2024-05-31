package com.example.myapplication.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.common.util.ThreadPoolManager
import com.example.myapplication.database.MessagesDatabase
import com.example.myapplication.entity.UserMessages
import com.example.myapplication.entity.MessagesEntity
import com.example.myapplication.entity.UserEntity
import com.example.myapplication.entity.toUserMessages
import com.example.myapplication.repository.MessagesRepository
import com.example.myapplication.repository.OfflineMessagesRepository
import com.example.myapplication.repository.OfflineUserRepository
import com.example.myapplication.repository.UserRepository
import com.example.myapplication.service.MessageService
import kotlinx.coroutines.flow.Flow
import io.github.oshai.kotlinlogging.KotlinLogging
import java.util.stream.Collectors


private val logger = KotlinLogging.logger {}

class MessagesViewModel(context: Context) : ViewModel() {

    /**
     * 消息列表
     * */
    val userMessagesList by lazy {
        ArrayList<UserMessages>()
    }

    val messageService: MessageService by lazy {
        MessageService(this)
    }

    class MessageViewModelFactory constructor(private val context: Context) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MessagesViewModel::class.java)) {
                return MessagesViewModel(context) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    private val itemsRepository: MessagesRepository by lazy {
        OfflineMessagesRepository(MessagesDatabase.getDatabase(context).messagesDao())
    }

    private val userRepository: UserRepository by lazy {
        OfflineUserRepository(MessagesDatabase.getDatabase(context).userDao())
    }

    /**
     * 获取最新的消息，并没有被确认的消息，按人进行分组 控制流
     * @param [sendUserId]
     * @param [recvUserId]
     * @param [onChange]
     */
    suspend fun getUserMessageLastByUserId(
        sendUserId: Long,
        recvUserId: Long,
        onChange: (ArrayList<UserMessages>) -> Unit
    ) {
        logger.info { "getUserMessageLastByUserId..." }
        itemsRepository.getUserMessageLastByRecvUserId(sendUserId, recvUserId).collect() { li ->
            val userIds = li.map { it.recvUserId } + li.map { it.sendUserId }
            userRepository.listByIds(userIds.distinct()).let { users ->
                val map = users.parallelStream().collect(Collectors.toMap(UserEntity::id) { it })
                val userMessages = ArrayList<UserMessages>()
                val sendRecv = ArrayList<String>()
                for (it in li) {
                    val send = map[it.sendUserId]
                    val recv = map[it.recvUserId]
                    val key =
                        if (it.sendUserId > it.recvUserId) "${it.sendUserId}_${it.recvUserId}" else "${it.recvUserId}_${it.sendUserId}"
                    if (sendRecv.contains(key)) {
                        continue
                    }
                    sendRecv.add(key)
                    if (send != null && recv != null) {
                        userMessages.add(
                            it.toUserMessages(
                                send.name,
                                send.imageUrl,
                                recv.name,
                                recv.imageUrl
                            )
                        )
                    }
                }
                onChange(userMessages)
            }
        }
    }

    /**
     * 查询往来记录
     * @param [sendUserId]
     * @param [recvUserId]
     * @param [page]
     * @param [pageSize]
     * @return [List<MessagesEntity>]
     */
    fun getMessagesSendAndRecvByUser(
        sendUserId: Long,
        recvUserId: Long,
        page: Int,
        pageSize: Int
    )
            : List<MessagesEntity> {
        return itemsRepository.getMessagesSendAndRecvByUser(sendUserId, recvUserId, page, pageSize)
    }

    /**
     * 查询和某人未确认的消息, 冰确认
     * @param [sendUserId]
     * @param [recvUserId]
     * @param [page]
     * @param [pageSize]
     * @return [Flow<List<MessagesEntity>>]
     */
    suspend fun getMessagesSendAndRecvFlowByUserAck(
        sendUserId: Long, recvUserId: Long,
        page: Int,
        pageSize: Int, onChange: (List<MessagesEntity>) -> Unit
    ) {
        itemsRepository.getMessagesSendAndRecvFlowByUser(sendUserId, recvUserId, page, pageSize)
            .collect() {
                onChange(it)
                ThreadPoolManager.getInstance().addTask("message") {
                    it.forEach { mess ->
                        messageService.sendMessagesEntity(mess)
                    }
                }
                it.forEach { mess ->
                    val temp = mess.copy()
                    temp.ack = 2
                    itemsRepository.updateItem(temp)
                }
            }
    }

    suspend fun saveItem(messagesEntity: MessagesEntity) {
        itemsRepository.insertItem(messagesEntity)
    }

    suspend fun updateItem(messagesEntity: MessagesEntity) {
        itemsRepository.updateItem(messagesEntity)
    }

}


