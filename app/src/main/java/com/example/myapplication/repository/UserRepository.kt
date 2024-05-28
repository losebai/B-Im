package com.example.myapplication.repository

import com.example.myapplication.dao.MessagesDao
import com.example.myapplication.dao.UserDao
import com.example.myapplication.entity.UserEntity
import kotlinx.coroutines.flow.Flow

interface  UserRepository : OfflineRepository<UserEntity, UserDao> {

    fun listByIds(ids: List<Long>) : Flow<List<UserEntity>>

}