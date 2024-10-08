package com.items.bim.common.util

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.getSystemService
import com.items.bim.common.provider.BaseContentProvider
import com.items.bim.entity.AppUserEntity
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

    suspend fun message(message: String, snackbarHostState: SnackbarHostState) {
        snackbarHostState.showSnackbar(
            message,
            actionLabel = "关闭",
            // Defaults to SnackbarDuration.Short
            duration = SnackbarDuration.Short
        )
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

    fun stringLen(str: String, len: Int = 15): String {
        return if (str.length > len) str.substring(0, 15) else str
    }

    private val names = arrayOf("白白的小迷妹","一个孤儿","牛逼的我","神人")
    fun randomName(): String {
        val index = Random.nextInt(names.size)
        val num = Random.nextInt()
        val name = names[index]
        return "$name$num"
    }

    fun randomUser() : com.items.bim.entity.AppUserEntity {
        val user = com.items.bim.entity.AppUserEntity()
        user.name = "游客";
        user.note = "个性签名";
        user.imageUrl = "https://pic.netbian.com/uploads/allimg/240429/225620-1714402580ab97.jpg"
        return user
    }


    @RequiresApi(Build.VERSION_CODES.O)
    var formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")


    @RequiresApi(Build.VERSION_CODES.O)
    fun localDateTimeToString(time: LocalDateTime) : String{
        return time.format(formatter)
    }

    fun stringEncrypt(str :String, size : Int) : String{
        val stringBuilder = StringBuilder()
        val len = str.length
        val s = size
        var e =  if (len  - size < 0) len else  len  - size
        for (i in 0..e){
            stringBuilder.append("*")
        }
        return "${str.substring(0, s)}${stringBuilder}${str.substring(s+stringBuilder.length..e)}"
    }


    fun copyToClipboard(text: String) {
        val clipboard = BaseContentProvider.context().getSystemService<ClipboardManager>() as ClipboardManager
        val clip = ClipData.newPlainText("label", text)
        clipboard.setPrimaryClip(clip)
    }

    fun pasteFromClipboard(): String? {
        val clipboard = BaseContentProvider.context().getSystemService<ClipboardManager>() as ClipboardManager
        return clipboard.primaryClip?.let { clip ->
            if (clip.itemCount > 0) {
                clip.getItemAt(0).coerceToText(BaseContentProvider.context()) as? String
            } else {
                null
            }
        }
    }
}