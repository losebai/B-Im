package com.example.myapplication.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.common.Mapping
import com.example.myapplication.database.MessagesDatabase
import com.example.myapplication.entity.UserEntity
import com.example.myapplication.remote.entity.AppUserEntity
import com.example.myapplication.repository.MessagesRepository
import com.example.myapplication.repository.OfflineMessagesRepository
import com.example.myapplication.repository.OfflineUserRepository
import com.example.myapplication.repository.UserRepository
import com.example.myapplication.service.UserService

class UserViewModel(context: Context): ViewModel() {

    private val userService: UserService = UserService()

    var userEntity = UserEntity()

    var sendUserEntity = UserEntity()

    var users: List<UserEntity> = mutableListOf()

    class MessageViewModelFactory constructor(private val context: Context ) : ViewModelProvider.Factory{

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
                return UserViewModel(context) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }

    }

    private val userRepository: UserRepository by lazy {
        OfflineUserRepository(MessagesDatabase.getDatabase(context).userDao())
    }

    fun getUserById(id: Long) : AppUserEntity{
        return userService.getUser(id)
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