package com.example.myapplication.repository

import com.example.myapplication.dao.BaseDao
import com.example.myapplication.entity.MessagesEntity
import kotlinx.coroutines.flow.Flow

abstract class OfflineRepository<T, M : BaseDao<T> >(private val m: M) : BaseRepository<T> {

     override suspend fun insertItem(t: T) = m.insert(t)

    override suspend fun deleteItem(t: T) =  m.delete(t)

    override suspend fun updateItem(t: T) = m.update(t)

}