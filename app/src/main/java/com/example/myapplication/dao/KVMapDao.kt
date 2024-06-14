package com.example.myapplication.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.myapplication.entity.KVMapEntity
import com.example.myapplication.entity.MessagesEntity


@Dao
interface KVMapDao :  BaseDao<KVMapEntity> {


    @Query("SELECT * FROM kv_map WHERE first = :key ORDER BY id DESC LIMIT 1")
    suspend fun findByKey(key: String): KVMapEntity?
}