package com.example.myapplication.repository

import com.example.myapplication.dao.BaseDao
import com.example.myapplication.dao.UserDao
import com.example.myapplication.entity.UserEntity

class OfflineUserRepository(private val userDao: UserDao) : UserRepository {
    override fun getDao(): BaseDao<UserEntity>  = userDao
}