package com.example.myapplication.repository

import com.example.myapplication.dao.BaseDao
import com.example.myapplication.dao.UserDao
import com.example.myapplication.entity.UserEntity
import kotlinx.coroutines.flow.Flow

class OfflineUserRepository(private val userDao: UserDao) : UserRepository {
    override fun getDao(): BaseDao<UserEntity>  = userDao

    override fun listByIds(ids: List<Long>) : Flow<List<UserEntity>> = userDao.listByIds(ids.toLongArray())
}