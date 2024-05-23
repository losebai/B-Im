package com.example.myapplication.common.util

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import com.example.myapplication.remote.entity.AppUserEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.noear.snack.core.utils.StringUtil
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random


object Utils {

    fun message(scope: CoroutineScope, message: String, snackbarHostState: SnackbarHostState) {
        scope.launch {
            snackbarHostState.showSnackbar(
                message,
                actionLabel = "关闭",
                // Defaults to SnackbarDuration.Short
                duration = SnackbarDuration.Short
            )
        }
    }

    internal fun Context.findActivity(): Activity {
        var context = this
        while (context is ContextWrapper) {
            if (context is Activity) return context
            context = context.baseContext
        }
        throw IllegalStateException("Permissions should be called in the context of an Activity")
    }

    fun stringOrNull(str: String): String {
        return if (StringUtil.isEmpty(str)) "" else str
    }

    private val names = arrayOf("白白的小迷妹","一个孤儿","牛逼的我","神人")
    fun randomName(): String {
        val index = Random.nextInt(names.size)
        val num = Random.nextInt()
        val name = names[index]
        return "$name$num"
    }

    fun randomUser() : AppUserEntity {
        val user = AppUserEntity()
        user.name = randomName();
        user.note = "那天，她说，她不会忘记我，可是我想太多";
        user.imageUrl = "https://pic.netbian.com/uploads/allimg/240429/225620-1714402580ab97.jpg"
        return user
    }


    var formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd HH:mm:ss")


    fun localDateTimeToString(time: LocalDateTime) : String{
        return time.format(formatter)
    }
}