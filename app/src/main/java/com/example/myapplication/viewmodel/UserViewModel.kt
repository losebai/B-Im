package com.example.myapplication.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.common.consts.SystemApp
import com.example.myapplication.common.util.Utils
import com.example.myapplication.database.AppDatabase
import com.example.myapplication.entity.UserEntity
import com.example.myapplication.event.GlobalInitEvent
import com.example.myapplication.remote.entity.AppUserEntity
import com.example.myapplication.remote.entity.toUserEntity
import com.example.myapplication.repository.impl.OfflineUserRepository
import com.example.myapplication.repository.UserRepository
import com.example.myapplication.service.UserService
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.stream.Collectors

private val logger = KotlinLogging.logger {
}

class UserViewModel(context: Context): ViewModel() {

    private val userService: UserService = UserService()

    // 当前用户
    var userEntity by mutableStateOf(Utils.randomUser().toUserEntity())

    // 当前读取用户
    var recvUserId = 0L

    // 联系人列表
    var users: List<UserEntity> = listOf()

    // id对应
    var userMap: MutableMap<Long, UserEntity> = mutableMapOf()

    class MessageViewModelFactory constructor(private val context: Context ) : ViewModelProvider.Factory{

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
                return UserViewModel(context) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }

    }

    private val userRepository: UserRepository by lazy {
        OfflineUserRepository(AppDatabase.getDatabase(context).userDao())
    }

    init {
        GlobalInitEvent.addUnit {
            val appUserEntity = Utils.randomUser()
            appUserEntity.deviceNumber = SystemApp.PRODUCT_DEVICE_NUMBER
            val user = this.gerUserByNumber(SystemApp.PRODUCT_DEVICE_NUMBER)
            if (user.id != 0L) {
                SystemApp.UserId = user.id
                SystemApp.USER_IMAGE = user.imageUrl
                appUserEntity.id = user.id
                this.userEntity = user.toUserEntity()
            } else {
                this.saveUser(appUserEntity)
            }
            logger.info { "${SystemApp.PRODUCT_DEVICE_NUMBER} 当前UserID: ${SystemApp.UserId}开始加载联系人" }
            val users = this.getReferUser(AppUserEntity())
            val map = users.parallelStream().collect(Collectors.toMap(UserEntity::id) { it })
            this.users = users
            this.userMap = map
        }
    }

    fun getUserById(id: Long) : AppUserEntity{
        return userService.getUser(id)
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun getLocalUserById(id :Long): UserEntity {
        var user = UserEntity()
        userMap[id].let { _user ->
            if(_user == null){
                GlobalScope.launch(Dispatchers.Default){
                    userRepository.getUser(id).also{
                        user = it
                    }
                }
            }else{
                user = _user
            }
        }
        return user
    }

    fun getReferUser(user : AppUserEntity) : List<UserEntity>{
        val userTemp = userService.getList(user)
        users = userTemp
        return userTemp
    }

    fun gerUserByNumber(str: String) : AppUserEntity{
        return userService.gerUserByNumber(str)
    }

    fun saveUser(user : AppUserEntity){
        userService.save(user)
    }


}