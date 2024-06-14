package com.example.myapplication.repository

import com.example.myapplication.dao.BaseDao
import kotlinx.coroutines.flow.Flow

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
}