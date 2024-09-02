package com.items.bim.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.items.bim.common.consts.LocalConfig
import com.items.bim.common.consts.SystemApp
import com.items.bim.common.util.HttpUtils
import com.items.bim.common.util.ThreadPoolManager
import com.items.bim.common.util.Utils
import com.items.bim.database.AppDatabase
import com.items.bim.dto.UserLoginDto
import com.items.bim.entity.AppUserEntity
import com.items.bim.entity.KVMapEntity
import com.items.bim.entity.toUserEntity
import com.items.bim.repository.KVMapRepository
import com.items.bim.repository.UserRepository
import com.items.bim.repository.impl.OfflineKVRepository
import com.items.bim.repository.impl.OfflineUserRepository
import com.items.bim.service.UserLoginService
import com.items.bim.service.UserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread

class UserLoginModel() : ViewModel() {

    private var token : String = ""

    var isLogin by mutableStateOf(false)

    var loginText = mutableStateOf("")

    val userLoginService = UserLoginService()

    private val userService: UserService = UserService.instance

    private val kvMapRepository: KVMapRepository by lazy {
        OfflineKVRepository(AppDatabase.getInstance().kvDao())
    }

    private suspend fun getLocalCookies() : String? {
        val cookies =  kvMapRepository.findByKey(LocalConfig.localCookies)
        return cookies?.value
    }

    suspend fun checkLogin() : Boolean{
        getLocalCookies() ?: return false
        return true
    }

    /**
     * 账密登录
     */
    fun login(userLogin: UserLoginDto, onIsLogin: (Boolean) -> Unit)  {
        userLoginService.login(userLogin, onResponse = {
            val isLogin = it.isNotEmpty()
            onIsLogin(isLogin)
            this.token = it
            Log.d("login", "token $it")
            MainScope().launch(Dispatchers.IO) {
                val cookies =  kvMapRepository.findByKey(LocalConfig.localCookies)
                if (cookies == null){
                    kvMapRepository.insertItem(KVMapEntity(key = LocalConfig.localCookies, value = it))
                }else{
                    cookies.value = it
                    kvMapRepository.updateItem(cookies)
                }
            }
            this.isLogin = true
        }, onError = {
            isLogin = false
            onIsLogin(false)
        })

    }

    /**
     * 游客登录
     */
    fun guestLogin() : AppUserEntity {
        val appUserEntity = Utils.randomUser()
        appUserEntity.deviceNumber = SystemApp.PRODUCT_DEVICE_NUMBER
        val user = userService.gerUserByNumber(SystemApp.PRODUCT_DEVICE_NUMBER)
        if (user.id != 0L) {
            SystemApp.UserId = user.id
            SystemApp.USER_IMAGE = user.imageUrl
            appUserEntity.id = user.id
        } else {
            userService.save(appUserEntity)
        }
        isLogin = true
        return user
    }

    fun logout(){
        isLogin = false
        MainScope().launch(Dispatchers.IO) {
            val cookies =  kvMapRepository.findByKey(LocalConfig.localCookies)
            if (cookies != null) {
                kvMapRepository.deleteItem(cookies)
            }
            userLoginService.logout()
        }
    }
}
