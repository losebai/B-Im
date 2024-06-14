package com.example.myapplication.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.common.Mapping
import com.example.myapplication.database.AppDatabase
import com.example.myapplication.entity.UserEntity
import com.example.myapplication.remote.entity.AppUserEntity
import com.example.myapplication.repository.OfflineUserRepository
import com.example.myapplication.repository.UserRepository
import com.example.myapplication.service.UserService
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserViewModel(context: Context): ViewModel() {

    private val userService: UserService = UserService()

    // 当前用户
    var userEntity by mutableStateOf(UserEntity())

    // 当前读取用户
    var recvUserId = 0L

    // 联系人列表
    var users: List<UserEntity> = mutableListOf()

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

    fun getUserById(id: Long) : AppUserEntity{
        return userService.getUser(id)
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun getLocalUserById(id :Long): UserEntity {
        var user: UserEntity = UserEntity()
        userMap[id].let { _user ->
            if(_user == null){
                GlobalScope.launch(Dispatchers.Default){
                    userRepository.getUser(id).collect(){
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
        val userTemp = Mapping.toUserEntityList(userService.getList(user))
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