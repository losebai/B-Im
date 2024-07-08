package com.items.bim.repository.impl

import com.items.bim.dao.BaseDao
import com.items.bim.dao.UserDao
import com.items.bim.entity.UserEntity
import com.items.bim.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class OfflineUserRepository(private val userDao: UserDao) : UserRepository {
    override fun getDao(): BaseDao<UserEntity>  = userDao

    override fun listByIds(ids: List<Long>) : List<UserEntity> = userDao.listByIds(ids.toLongArray())

    override fun all(): Flow<List<UserEntity>> = userDao.all()

    override fun getUser(id: Long): UserEntity = userDao.getItem(id)
}