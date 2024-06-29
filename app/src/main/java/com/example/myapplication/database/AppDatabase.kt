package com.example.myapplication.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.myapplication.common.provider.BaseContentProvider
import com.example.myapplication.dao.KVMapDao
import com.example.myapplication.dao.McRecordDao
import com.example.myapplication.dao.MessagesDao
import com.example.myapplication.dao.UserDao
import com.example.myapplication.entity.KVMapEntity
import com.example.myapplication.entity.McRecordEntity
import com.example.myapplication.entity.MessagesEntity
import com.example.myapplication.entity.UserEntity

@Database(
    entities = [MessagesEntity::class, UserEntity::class,
        KVMapEntity::class, McRecordEntity::class],
    version = 5
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun messagesDao(): MessagesDao

    abstract fun userDao(): UserDao


    abstract fun kvDao(): KVMapDao

    abstract fun McRecordDao(): McRecordDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance() = instance ?: kotlin.synchronized(AppDatabase::class.java) {
            instance ?: buildDatabase(BaseContentProvider.context())
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "app-database"
            ).addMigrations(MIGRATION_2_3)
                .addMigrations(MIGRATION_3_4)
                .build()
                .also {
                    instance = it
                }
        }

        fun getDatabase(context: Context): AppDatabase {
            return instance ?: kotlin.synchronized(this) {
                buildDatabase(context)
            }
        }

        @JvmStatic
        suspend fun set(key: String, value: String): Boolean {
            return getInstance().setValue(key, value)
        }

        @JvmStatic
        suspend fun get(key: String): String {
            return getInstance().getValue(key)
        }


        fun closeDB() {
            getInstance().close()
        }

        val MIGRATION_2_3: Migration = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                Log.i("Room_StudentDatabase", "数据库版本 2 升级到 版本 3")
                database.execSQL("alter table app_users add column userStatus integer not null default 0")
            }
        }
        val MIGRATION_3_4: Migration = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                Log.i("Room_StudentDatabase", "数据库版本 2 升级到 版本 3")
                database.execSQL("alter table app_users RENAME  column userStatus to status")
            }
        }
    }

    suspend fun setValue(key: String, value: String): Boolean {
        return try {
            var kv = kvDao().findByKey(key)
            if (kv == null) {
                kv = KVMapEntity(key = key, value = value)
                val id = kvDao().insert(kv) //返回 主键值 > -1 表示 insert 成功
                id > -1
            } else {
                kv.value = value
                val up = kvDao().update(kv) //返回 更新数 > 0表示 update 成功
                up > 0
            }
        } catch (e: Exception) {
            Log.e(this.javaClass.name, e.message.toString())
            false
        }
    }

    suspend fun getValue(key: String): String {
        return try {
            kvDao().findByKey(key)?.value ?: ""
        } catch (e: Exception) {
            Log.e(this.javaClass.name, e.message.toString())
            ""
        }
    }

    override fun close() {
        super.close()
        //数据库关闭后把instance置空
        instance = null
    }
}