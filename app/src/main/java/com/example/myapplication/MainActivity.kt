package com.example.myapplication

import android.os.Build
import android.os.Bundle
import android.view.ActionMode
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.OptIn
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.media3.common.util.UnstableApi
import androidx.media3.common.util.Util
import coil.Coil
import coil.ImageLoader
import com.example.myapplication.common.consts.SystemApp
import com.example.myapplication.common.provider.PlayerProvider
import com.example.myapplication.common.util.ThreadPoolManager
import com.example.myapplication.common.util.Utils
import com.example.myapplication.entity.UserEntity
import com.example.myapplication.event.GlobalInitEvent
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
import kotlin.concurrent.thread


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
        logger.info { "onRestart" }
    }

    override fun onDestroy() {
        super.onDestroy()
        messagesViewModel.messageService.close()
        ThreadPoolManager.getInstance().exitThreadPool("init")
        ThreadPoolManager.getInstance().exitThreadPool("lottery")
        ThreadPoolManager.getInstance().exitThreadPool("message")
        logger.info { "onDestroy" }
    }

    private fun initLoad(){
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
            messagesViewModel.messageService.start()
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
                MainNavGraph(this,
                    appBase,
                    userViewModel,
                    messagesViewModel,
                    appBase.imageViewModel,init={
                        this.initLoad()
                    })
            }
        }
    }

}
