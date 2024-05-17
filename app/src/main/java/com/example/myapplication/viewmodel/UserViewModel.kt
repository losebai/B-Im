package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import com.example.myapplication.entity.UserEntity
import com.example.myapplication.remote.entity.AppUserEntity
import com.example.myapplication.service.UserService

class UserViewModel: ViewModel() {

    private val userService: UserService = UserService()

    var userEntity = UserEntity()

    var users: List<UserEntity> = mutableListOf()

    fun getUserById(id: Long) : AppUserEntity{
        return userService.getUser(id)
    }

    fun getReferUser(user : AppUserEntity) : List<AppUserEntity>{
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