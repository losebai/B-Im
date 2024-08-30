package com.items.bim.dao

import androidx.room.Dao
import androidx.room.Query
import com.items.bim.entity.KVMapEntity


@Dao
interface KVMapDao :  BaseDao<KVMapEntity> {


    @Query("SELECT * FROM kv_map WHERE first = :key  LIMIT 1")
    suspend fun findByKey(key: String): KVMapEntity?
}