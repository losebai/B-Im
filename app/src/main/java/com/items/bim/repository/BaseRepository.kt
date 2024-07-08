package com.items.bim.repository

import com.items.bim.dao.BaseDao

interface BaseRepository<T> {

    fun getDao() : BaseDao<T>

    /**
     * Insert T in the data source
     */
    suspend fun insertItem(t: T) : Long

    /**
     * Delete T from the data source
     */
    suspend fun deleteItem(t: T)

    /**
     * Update T in the data source
     */
    suspend fun updateItem(t: T) : Int


    /**
     * Insert T in the data source
     */
    suspend fun insertItemBatch(t: List<T>)
}