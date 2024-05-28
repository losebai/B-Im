package com.example.myapplication.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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
import java.util.stream.Collector
import java.util.stream.Collectors


class MessagesViewModel(context: Context) : ViewModel() {

    val userMessagesList = mutableListOf<UserMessages>()

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

    suspend fun getUserMessageLastByUserId(
        sendUserId: Long,
        recvUserId: Long,
    ): List<UserMessages> {
        val userMessages = ArrayList<UserMessages>()
        itemsRepository.getUserMessageLastByRecvUserId(sendUserId, recvUserId).collect { li ->
            val userIds = li.map { it.recvUserId } + li.map { it.sendUserId }
            userRepository.listByIds(userIds.distinct()).collect { users ->
                val map = users.stream().collect(Collectors.toMap(UserEntity::id) { it })
                for (it in li){
                    val send = map[it.sendUserId]
                    val recv = map[it.recvUserId]
                    if (send != null && recv != null){
                        userMessages.add(it.toUserMessages(send.name, send.imageUrl, recv.name, recv.imageUrl))
                    }
                }
            }
        }
        return userMessages
    }

    suspend fun getMessagesSendAndRecvByUser(
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


