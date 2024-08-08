package com.items.bim.common.util

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

object DateUtils {

    @SuppressLint("SimpleDateFormat")
    val sdf = SimpleDateFormat("MM/dd")


    fun timestampToDateStr(timestamp :Long) : String {
        val date = Date(timestamp)
        return sdf.format(date)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun timestampToDate(timestamp: Long): LocalDateTime {
        // 将时间戳转换为Instant
        val instant = Instant.ofEpochMilli(timestamp)
        // 转换Instant为LocalDateTime，使用默认时区
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
    }
}