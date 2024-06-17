package com.example.myapplication

import android.os.Build
import android.os.Bundle
import android.view.ActionMode
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import coil.Coil
import coil.ImageLoader
import com.example.myapplication.common.consts.SystemApp
import com.example.myapplication.common.util.ThreadPoolManager
import com.example.myapplication.common.util.Utils
import com.example.myapplication.entity.UserEntity
import com.example.myapplication.event.ViewModelEvent
import com.example.myapplication.remote.entity.AppUserEntity
import com.example.myapplication.remote.entity.toUserEntity
import com.example.myapplication.ui.AppTheme
import com.example.myapplication.viewmodel.MessagesViewModel
import com.example.myapplication.viewmodel.UserViewModel
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.util.stream.Collectors


private val logger = KotlinLogging.logger {
}


class MainActivity : AppCompatActivity() {


    private var appBase: AppBase = AppBase()

    private lateinit var userViewModel: UserViewModel;

    private lateinit var messagesViewModel: MessagesViewModel;


    private val viewModelEvent: ViewModelEvent = ViewModelEvent.getInstance(this)

    override fun onRestart() {
        super.onRestart()
        messagesViewModel.messageService.reconnect()
    }

//    override fun onStop() {
//        messagesViewModel.messageService.close()
//        super.onStop()
//    }

    override fun onDestroy() {
        super.onDestroy()
        messagesViewModel.messageService.close()
    }

    private fun initLoad() {
        Coil.setImageLoader(ImageLoader(this))
        // 初始化的时候保存和更新
        // 默认账户信息
        ThreadPoolManager.getInstance().addTask("init") {
            val appUserEntity = Utils.randomUser()
            appUserEntity.deviceNumber = SystemApp.PRODUCT_DEVICE_NUMBER
            try {
                val user = userViewModel.gerUserByNumber(SystemApp.PRODUCT_DEVICE_NUMBER)
                if (user.id != 0L) {
                    SystemApp.UserId = user.id
                    SystemApp.USER_IMAGE = user.imageUrl
                    appUserEntity.id = user.id
                    userViewModel.userEntity = user.toUserEntity()
                } else {
                    userViewModel.saveUser(appUserEntity)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            logger.info { "开始加载联系人" }
            val users = userViewModel.getReferUser(AppUserEntity())
            val map = users.parallelStream().collect(Collectors.toMap(UserEntity::id) { it })
            userViewModel.users = users
            userViewModel.userMap = map
            appBase.imageViewModel.getDay7Images(this)
            messagesViewModel.messageService.sendText("", "")
        }
        MainScope().launch {
            viewModelEvent.onUserMessageLastByUserId(
                this@MainActivity,
                SystemApp.UserId,
                SystemApp.UserId,
                userViewModel
            ) { userMessages ->
                if (userMessages.isNotEmpty()) {
                    messagesViewModel.userMessagesList.clear()
                    messagesViewModel.userMessagesList.addAll(userMessages)
                }
            }
            viewModelEvent.onUserAll(this@MainActivity, userViewModel)
            Utils.message(this, "程序初始化完成", SystemApp.snackBarHostState)
        }
    }


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userViewModel = ViewModelProvider(
            this,
            UserViewModel.MessageViewModelFactory(this)
        )[UserViewModel::class.java]
        messagesViewModel =
            ViewModelProvider(
                this,
                MessagesViewModel.MessageViewModelFactory(this)
            )[MessagesViewModel::class.java]
        setContent {
            AppTheme(appBase.darkTheme) {
                MainNavGraph(
                    appBase,
                    userViewModel,
                    messagesViewModel,
                    appBase.imageViewModel,
                    init = { this.initLoad() })
            }
        }
    }
}
