package com.example.myapplication.repository

import kotlinx.coroutines.flow.Flow

interface BaseRepository<T> {


    /**
     * Insert T in the data source
     */
    suspend fun insertItem(t: T)

    /**
     * Delete T from the data source
     */
    suspend fun deleteItem(t: T)

    /**
     * Update T in the data source
     */
    suspend fun updateItem(t: T)
}