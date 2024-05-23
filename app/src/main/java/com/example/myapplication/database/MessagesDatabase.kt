package com.example.myapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.dao.MessagesDao
import com.example.myapplication.dao.UserDao
import com.example.myapplication.entity.MessagesEntity
import com.example.myapplication.entity.UserEntity
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [MessagesEntity::class, UserEntity::class], version = 1)
abstract class MessagesDatabase : RoomDatabase() {

    abstract fun messagesDao(): MessagesDao

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var Instance: MessagesDatabase? = null

        @OptIn(InternalCoroutinesApi::class)
        fun getDatabase(context: Context): MessagesDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, MessagesDatabase::class.java, "messages_database")
                    .build().also {
                        //  以保留对最近创建的数据库实例的引用
                        Instance = it
                    }
            }
        }
    }
}