package com.example.myapplication.viewmodel

import android.content.Context
import android.widget.ListAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
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
import kotlinx.coroutines.flow.Flow
import mu.KotlinLogging
import java.util.stream.Collector
import java.util.stream.Collectors


private val logger = KotlinLogging.logger {}

class MessagesViewModel(context: Context) : ViewModel() {

    var userMessagesList = mutableListOf<UserMessages>()

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

    suspend fun getUserMessageLastByUserId(context: LifecycleOwner,
                                   sendUserId: Long,
                                   recvUserId: Long,
    ): List<UserMessages> {
        logger.info { "getUserMessageLastByUserId..." }
        val userMessages = ArrayList<UserMessages>()
        itemsRepository.getUserMessageLastByRecvUserId(sendUserId, recvUserId).asLiveData().observe(context) { li ->
            val userIds = li.map { it.recvUserId } + li.map { it.sendUserId }
            ThreadPoolManager.getInstance().addTask("getUserMessageLastByUserId"){
                userRepository.listByIds(userIds.distinct()).let { users ->
                    val map = users.parallelStream().collect(Collectors.toMap(UserEntity::id) { it })
                    for (it in li) {
                        val send = map[it.sendUserId]
                        val recv = map[it.recvUserId]
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
                }
            }
        }
        logger.info { "getUserMessageLastByUserId... ok" }
        return userMessages
    }

    fun getMessagesSendAndRecvByUser(
        sendUserId: Long,
        recvUserId: Long,
        page: Int,
        pageSize: Int
    )
            : Flow<List<MessagesEntity>> {
        return itemsRepository.getMessagesSendAndRecvByUser(sendUserId, recvUserId, page, pageSize)
    }

    suspend fun saveItem(messagesEntity: MessagesEntity) {
        itemsRepository.insertItem(messagesEntity)
    }

    suspend fun updateItem(messagesEntity: MessagesEntity) {
        itemsRepository.updateItem(messagesEntity)
    }

}


