package com.example.myapplication.event

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.asLiveData
import com.example.myapplication.database.AppDatabase
import com.example.myapplication.entity.UserEntity
import com.example.myapplication.entity.UserMessages
import com.example.myapplication.entity.toUserMessages
import com.example.myapplication.repository.MessagesRepository
import com.example.myapplication.repository.impl.OfflineMessagesRepository
import com.example.myapplication.repository.impl.OfflineUserRepository
import com.example.myapplication.repository.UserRepository
import com.example.myapplication.viewmodel.UserViewModel
import io.github.oshai.kotlinlogging.KotlinLogging
import java.util.stream.Collectors

private val logger = KotlinLogging.logger {}

/**
 * 这里禁止使用io, 线程
 * @author 11450
 * @date 2024/05/28
 * @constructor 创建[ViewModelEvent]
 * @param [context]
 */
open class ViewModelEvent private constructor(context: Context) {

    companion object {

        private var instance: ViewModelEvent? = null
        @Synchronized fun getInstance(context: Context): ViewModelEvent {
            if (instance == null) {
                instance = ViewModelEvent(context)
            }
            return instance!!
        }
    }


    private val itemsRepository: MessagesRepository by lazy {
        OfflineMessagesRepository(AppDatabase.getDatabase(context).messagesDao())
    }

    private val userRepository: UserRepository by lazy {
        OfflineUserRepository(AppDatabase.getDatabase(context).userDao())
    }



    suspend fun onUserMessageLastByUserId(owner: LifecycleOwner,
                                          sendUserId: Long,
                                          recvUserId: Long,
                                          userViewModel: UserViewModel,
                                          onChange: (ArrayList<UserMessages>) -> Unit){
        logger.info { "onUserMessageLastByUserId..." }
        itemsRepository.getUserMessageLastByRecvUserId(sendUserId, recvUserId).asLiveData().observe(owner) { li ->
            val map = userViewModel.userMap
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

    suspend fun onUserAll(owner: LifecycleOwner, userViewModel: UserViewModel){
        logger.info { "onUserAll..." }
        userRepository.all().asLiveData().observe(owner) { users ->
            val map = users.parallelStream().collect(Collectors.toMap(UserEntity::id) { it })
            if (map.isNotEmpty()){
                userViewModel.userMap = map
            }
        }
    }
}