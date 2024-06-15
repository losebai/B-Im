package com.example.myapplication.entity

import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.myapplication.common.consts.UserStatus
import com.example.myapplication.remote.entity.AppUserEntity

class UserConverters{
    @TypeConverter
    fun fromStatus(value: UserStatus): Int{
        return value.value
    }

    @TypeConverter
    fun toStatus(value: Int) =  enumValues<UserStatus>()[value]
}


@Entity(tableName = "app_users")
@TypeConverters(UserConverters::class)
data class UserEntity (
    @PrimaryKey
    var id: Long = 0L,
    var name: String = "",
    var imageUrl: String = "",
    var note: String = "",
    var createTime: String = "",
    val status: UserStatus = UserStatus.INIT,
)

fun UserEntity.toAppUserEntity(deviceNumber: String) = AppUserEntity(id, name, imageUrl, note, deviceNumber,  status)