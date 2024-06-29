package com.example.myapplication.repository

import com.example.myapplication.dao.BaseDao
import com.example.myapplication.entity.MessagesEntity
import kotlinx.coroutines.flow.Flow

interface OfflineRepository<T, M : BaseDao<T> > : BaseRepository<T> {

     override suspend fun insertItem(t: T) = getDao().insert(t)

    override suspend fun deleteItem(t: T) =  getDao().delete(t)

    override suspend fun updateItem(t: T) = getDao().update(t)

    override suspend fun insertItemBatch(t: List<T>) = getDao().insertBatch(t)
}