package com.example.myapplication.common.consts

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.myapplication.common.util.ImageUtils
import com.example.myapplication.remote.entity.AppUserEntity


object SystemApp {

    // 设备唯一识别吗
    val PRODUCT_DEVICE_NUMBER: String = android.os.Build.FINGERPRINT ?: ""

    var UserId =  0L

    var USER_IMAGE: String by mutableStateOf("")

    var snackBarHostState = SnackbarHostState()


    val IMAGE_PATHS = arrayOf(ImageUtils.PICTURES_PATH, ImageUtils.DCIM_PATH)

    var userStatus by mutableStateOf(UserStatus.INIT)


//val names = arrayOf("本机相册","相机","QQ","微信" )

}

