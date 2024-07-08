package com.items.bim.dao

import androidx.room.Dao
import androidx.room.Query
import com.items.bim.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao :  BaseDao<UserEntity> {

    @Query("select * from app_users where id = :id")
    fun getItem(id: Long): UserEntity

    @Query("select * from app_users where id in (:ids)")
    fun listByIds(ids :  LongArray) : List<UserEntity>

    @Query("select * from app_users")
    fun all() : Flow<List<UserEntity>>

}