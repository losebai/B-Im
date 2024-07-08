package com.items.bim.repository

import com.items.bim.dao.UserDao
import com.items.bim.entity.UserEntity
import kotlinx.coroutines.flow.Flow

interface  UserRepository : OfflineRepository<UserEntity, UserDao> {

    fun listByIds(ids: List<Long>) : List<UserEntity>

    fun all() : Flow<List<UserEntity>>

    fun getUser(id : Long) : UserEntity

}