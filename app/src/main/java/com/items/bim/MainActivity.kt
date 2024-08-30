package com.items.bim

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.os.PersistableBundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.items.bim.common.consts.SystemApp
import com.items.bim.common.ui.AppTheme
import com.items.bim.common.util.ThreadPoolManager
import com.items.bim.common.util.Utils
import com.items.bim.event.ViewModelEvent
import com.items.bim.viewmodel.HomeViewModel
import com.items.bim.viewmodel.MessagesViewModel
import com.items.bim.viewmodel.UserViewModel
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


private val logger = KotlinLogging.logger {
}


class MainActivity : AppCompatActivity() {

    private lateinit var homeViewModel: HomeViewModel;

    private lateinit var userViewModel: UserViewModel;

    private lateinit var messagesViewModel: MessagesViewModel;

    private val viewModelEvent: ViewModelEvent = ViewModelEvent.getInstance(this)


    private val connection  = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val sessionBinder = service as SessionBinder
            sessionBinder.messagesViewModel = messagesViewModel
            messagesViewModel.messageService.reconnect()
        }

        @Override
        override fun  onServiceDisconnected(name: ComponentName) {
            messagesViewModel.messageService.close()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        val startIntent = Intent(this, AppStartService::class.java)
        bindService(startIntent, connection, BIND_AUTO_CREATE)
//        startService(startIntent) // 启动服务
    }

    override fun onRestart() {
        super.onRestart()
    }

    override fun onDestroy() {
        super.onDestroy()
        ThreadPoolManager.getInstance().exitThreadPool()
        logger.debug { "onDestroy" }
    }

    suspend fun initLoad() {
        // 监听消息列表
        viewModelEvent.onUserMessageLastByUserId(
            this@MainActivity,
            SystemApp.UserId,
            SystemApp.UserId,
            userViewModel
        ) { userMessages ->
            if (userMessages.isNotEmpty()) {
                messagesViewModel.userMessagesList = userMessages
                Log.d("onUserMessageLastByUserId", userMessages.toString())
            }
        }

        // 监听用户列表
        viewModelEvent.onUserAll(this@MainActivity, userViewModel)
        messagesViewModel.messageService.start()
        Utils.message("程序初始化完成", SystemApp.snackBarHostState)
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
//            MultiplePermissions(
//                listOf(
//                    Manifest.permission.QUERY_ALL_PACKAGES,
//                    Manifest.permission.CAMERA,
//                    Manifest.permission.READ_MEDIA_IMAGES,
//                    Manifest.permission.MANAGE_EXTERNAL_STORAGE,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE
//                )
//            )
            homeViewModel = viewModel<HomeViewModel>()
            AppTheme(homeViewModel.darkTheme) {
                MainNavGraph(this,
                    homeViewModel,
                    userViewModel,
                    messagesViewModel,
                     init = {
                        this.initLoad()
                    })
            }
        }
    }

}
