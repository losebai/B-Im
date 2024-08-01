package com.items.bim.repository

import com.items.bim.dao.BaseDao

interface OfflineRepository<T, M : BaseDao<T> > : BaseRepository<T> {

     override suspend fun insertItem(t: T) = getDao().insert(t)

    override suspend fun deleteItem(t: T) =  getDao().delete(t)

    override suspend fun updateItem(t: T) = getDao().update(t)


    override suspend fun updateItemBatch(t: List<T>) = getDao().updateBatch(t)

    override suspend fun insertItemBatch(t: List<T>) = getDao().insertBatch(t)
}