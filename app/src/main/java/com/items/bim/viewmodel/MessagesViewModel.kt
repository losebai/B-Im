package com.items.bim.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.items.bim.common.consts.AppEventConst
import com.items.bim.common.util.ThreadPoolManager
import com.items.bim.database.AppDatabase
import com.items.bim.entity.UserMessages
import com.items.bim.entity.MessagesEntity
import com.items.bim.entity.UserEntity
import com.items.bim.entity.toUserMessages
import com.items.bim.event.GlobalInitEvent
import com.items.bim.repository.MessagesRepository
import com.items.bim.repository.impl.OfflineMessagesRepository
import com.items.bim.repository.impl.OfflineUserRepository
import com.items.bim.repository.UserRepository
import com.items.bim.service.MessageService
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
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
        OfflineMessagesRepository(AppDatabase.getDatabase(context).messagesDao())
    }

    private val userRepository: UserRepository by lazy {
        OfflineUserRepository(AppDatabase.getDatabase(context).userDao())
    }

    private val _uiMessageState = MutableStateFlow(listOf<MessagesEntity>())

    // The UI collects from this StateFlow to get its state updates
    val uiMessageState: StateFlow<List<MessagesEntity>> = _uiMessageState

    init {
        GlobalInitEvent.addUnit{
            messageService.sendText(AppEventConst.OFF_LINE_USER_MESSAGE, "")
        }
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
    fun getMessagesSendAndRecvFlowByUserAck(
        sendUserId: Long, recvUserId: Long,
        page: Int,
        pageSize: Int
    ) : List<MessagesEntity>{
        return itemsRepository.getMessagesSendAndRecvFlowByUser(sendUserId, recvUserId, page, pageSize)
            .let {
                if (it.isNotEmpty()){
                    // 发送到UI数据流
                    _uiMessageState.value = it
                    ThreadPoolManager.getInstance().addTask("message") {
                        it.forEach { mess ->
                            mess.ack = 1
                            messageService.sendMessagesEntity(mess)
                        }
                    }
                }
                Log.d("Message Flow", "getMessagesSendAndRecvFlowByUser ${it.size}")
                it
            }
    }

    suspend fun saveItem(messagesEntity: MessagesEntity) {
        itemsRepository.insertItem(messagesEntity)
    }

    suspend fun updateItemBatch(messagesEntitys: List<MessagesEntity>) : Int{
        return itemsRepository.updateItemBatch(messagesEntitys)
    }

}


