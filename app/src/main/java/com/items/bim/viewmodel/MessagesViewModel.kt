package com.items.bim.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.items.bim.common.consts.AppEventConst
import com.items.bim.common.consts.AppUserAck
import com.items.bim.common.consts.SystemApp
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.stream.Collectors


private val logger = KotlinLogging.logger {}

class MessagesViewModel(context: Context) : ViewModel() {

    private val scope = CoroutineScope(Dispatchers.Default)

    /**
     * 消息列表
     * */
    var userMessagesList by mutableStateOf(listOf<UserMessages>())

    // 与接受人的消息列表
    val messages = ArrayList<MessagesEntity>()

    val messageService: MessageService by lazy {
        MessageService(this)
    }

    var isOnUserMessageLister = false


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

    init {
        GlobalInitEvent.addUnit{
            messageService.sendText(AppEventConst.OFF_LINE_USER_MESSAGE, "")
        }
    }

    /**
     * 查询和某人未确认的消息, 冰确认
     * @param [sendUserId]
     * @param [recvUserId]
     * @param [page]
     * @param [pageSize]
     * @return [Flow<List<MessagesEntity>>]
     */
    fun onUserMessageLister(coroutineScope: CoroutineScope, recvUserId: Long, onChange: (MessagesEntity) -> Unit) : Job {
        return coroutineScope.launch(Dispatchers.IO)  {
            val list = itemsRepository.getMessagesSendAndRecvFlowByUser(
                recvUserId,
                SystemApp.UserId,
                1,
                100
            )
            var tempMessageId = ""
            list.collect {
                logger.info { "一直有多少条消息${SystemApp.UserId}:${ recvUserId}:${it.size}" }
                // 发送已读确认
                ThreadPoolManager.getInstance().addTask("message") {
                    it.forEach { mess ->
                        mess.recvDateTime = System.currentTimeMillis()
                        messageService.sendMessagesEntity(mess)
                    }
                }
                for (mess in it){
                    // 将未读给确认已读
                    if (mess.ack == AppUserAck.ACK_OK.value){
                        mess.ack = AppUserAck.READ_OK.value
                    }
                    // 重复校验
                    if (mess.messagesId == tempMessageId){
                        continue
                    }
                    tempMessageId = mess.messagesId
                    onChange(mess)
                }
                val num = this@MessagesViewModel.updateItemBatch(it)
                Log.d("uiMessageState ", "已确认 $num")
            }
        }
    }

    /**
     * 获取最新的消息，并没有被确认的消息，按人进行分组
     *  在 ViewModelEvent 已有实现
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
                        it.sendUserName = send.name;
                        it.sendUserImageUri = send.imageUrl;
                        it.recvUserName = recv.name;
                        it.recvUserImageUri = recv.imageUrl;
                        userMessages.add(it)
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
    ) = itemsRepository.getMessagesSendAndRecvByUser(sendUserId, recvUserId, page, pageSize)


    suspend fun saveItem(messagesEntity: MessagesEntity) {
        itemsRepository.insertItem(messagesEntity)
    }

    suspend fun updateItemBatch(messagesEntitys: List<MessagesEntity>) : Int{
        return itemsRepository.updateItemBatch(messagesEntitys)
    }

    fun sendUserMessage(messages: MessagesEntity){
        scope.launch {
            itemsRepository.insertItem(messages)
        }
        ThreadPoolManager.getInstance().addTask("message") {
            messageService.sendMessagesEntity(messages)
        }
    }
}


