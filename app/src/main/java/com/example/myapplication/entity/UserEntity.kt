package com.example.myapplication.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.myapplication.common.consts.UserStatus
import com.example.myapplication.remote.entity.AppUserEntity

class UserConverters{
    @TypeConverter
    fun fromStatus(value: UserStatus): Int{
        return value.value
    }

    @TypeConverter
    fun toStatus(value: Int) =  UserStatus.toUserStatus(value)
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
    var status: UserStatus = UserStatus.INIT,
)

fun UserEntity.toAppUserEntity(deviceNumber: String) = AppUserEntity(id, name, imageUrl, note, deviceNumber,  status)