package com.example.myapplication.common.util

import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.example.myapplication.database.AppDatabase
import com.example.myapplication.entity.UserEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.noear.snack.ONode

/**
 * 数据持久化辅助类
 */
object WanHelper {

    private const val SEARCH_HISTORY = "search_history"
    private const val WEB_BOOKMARK = "web_bookmark"
    private const val WEB_HISTORY = "web_history"
    private const val UI_MODE = "ui_mode"
    private const val USER = "user"


    /**
     * 设置搜索历史
     */
    suspend fun setSearchHistory(list: List<String>) {
        AppDatabase.set(SEARCH_HISTORY, ONode.load(list).toJson())
    }

    /**
     * 获取搜索历史
     */
    suspend fun getSearchHistory(): List<String> {
        return try {
            val json = AppDatabase.get(SEARCH_HISTORY)
            ONode.deserialize<List<String>>(json) ?: ArrayList()
        } catch (e: Exception) {
            Log.e(this.javaClass.name, e.message.toString())
            ArrayList()
        }
    }

    suspend fun setWebBookmark(list: List<String>) {
        AppDatabase.set(WEB_BOOKMARK, ONode.load(list).toJson())
    }

    suspend fun getWebBookmark(): List<String> {
        return try {
            val json = AppDatabase.get(WEB_BOOKMARK)
            ONode.deserialize<List<String>>(json) ?: ArrayList()
        } catch (e: Exception) {
            Log.e(this.javaClass.name, e.message.toString())
            ArrayList()
        }
    }

    suspend fun setWebHistory(list: List<String>) {
        AppDatabase.set(WEB_HISTORY, ONode.load(list).toJson())
    }

    suspend fun getWebHistory(): List<String> {
        return try {
            val json = AppDatabase.get(WEB_HISTORY)
            ONode.deserialize<List<String>>(json) ?: ArrayList()
        } catch (e: Exception) {
            Log.e(this.javaClass.name, e.message.toString())
            ArrayList()
        }
    }



    /**
     * mode :
     *      AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM,
     *      AppCompatDelegate.MODE_NIGHT_NO,
     *      AppCompatDelegate.MODE_NIGHT_YES
     */
    suspend fun setUiMode(mode: Int): Boolean {
        return AppDatabase.set(UI_MODE, mode.toString())
    }

    /**
     * 显示模式状态
     * return
     *       AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM,
     *       AppCompatDelegate.MODE_NIGHT_NO,
     *       AppCompatDelegate.MODE_NIGHT_YES
     */
    suspend fun getUiMode(): Int {
        return try {
            AppDatabase.get(UI_MODE).toInt()
        } catch (e: Exception) {
            Log.e(this.javaClass.name, e.message.toString())
            AppCompatDelegate.MODE_NIGHT_NO
        }
    }

    /**
     * 设置用户信息
     */
    suspend fun setUser(user: UserEntity?) {
        user?.let {
            AppDatabase.set(USER, ONode.load(user).toJson())
        }
    }

    /**
     * 获取用户信息
     */
    suspend fun getUser(): UserEntity {
        return try {
            ONode.deserialize<UserEntity>(AppDatabase.get(USER)) ?: UserEntity()
        } catch (e: Exception) {
            Log.e(this.javaClass.name, e.message.toString())
            UserEntity()
        }
    }

    fun getUser(result: (UserEntity) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val userBean = getUser()
            withContext(Dispatchers.Main) {
                result.invoke(userBean)
            }
        }
    }

    /**
     * 关闭数据库
     */
    fun close() {
        AppDatabase.closeDB()
    }

}