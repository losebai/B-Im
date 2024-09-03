package com.items.bim.event

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.asLiveData
import com.items.bim.database.AppDatabase
import com.items.bim.entity.UserEntity
import com.items.bim.entity.UserMessages
import com.items.bim.entity.toUserMessages
import com.items.bim.repository.MessagesRepository
import com.items.bim.repository.impl.OfflineMessagesRepository
import com.items.bim.repository.impl.OfflineUserRepository
import com.items.bim.repository.UserRepository
import com.items.bim.viewmodel.UserViewModel
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