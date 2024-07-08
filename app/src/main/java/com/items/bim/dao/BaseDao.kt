package com.items.bim.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: T) : Long


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBatch(items: List<T>)

    @Update
    suspend fun update(item: T) : Int

    @Delete
    suspend fun delete(item: T)


//    @Query("${(T::class.java.annotations.find { it is Entity } as Entity).tableName}")
//    fun getItem(id: Int): Flow<T>

//    fun getAllItems(): Flow<List<T>>
}