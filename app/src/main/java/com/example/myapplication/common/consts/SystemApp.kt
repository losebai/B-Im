package com.example.myapplication.common.consts

import androidx.compose.material3.SnackbarHostState
import com.example.myapplication.common.util.ImageUtils


object SystemApp {

    // 设备唯一识别吗
    val PRODUCT_DEVICE_NUMBER: String = android.os.Build.FINGERPRINT

    var UserId =  0L


    var snackBarHostState = SnackbarHostState()


    val IMAGE_PATHS = arrayOf(ImageUtils.PICTURES_PATH, ImageUtils.DCIM_PATH)

    var userStatus = UserStatus.INIT


//val names = arrayOf("本机相册","相机","QQ","微信" )

}

