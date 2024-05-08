package com.example.myapplication.common.consts

import android.content.Context
import android.content.Context.WIFI_SERVICE
import android.net.NetworkCapabilities
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import androidx.collection.mutableLongSetOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import okhttp3.OkHttpClient


// 设备唯一识别吗
val PRODUCT_DEVICE_NUMBER: String = android.os.Build.FINGERPRINT

var UserId =  0L