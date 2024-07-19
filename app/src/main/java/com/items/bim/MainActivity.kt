package com.items.bim

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.items.bim.common.consts.SystemApp
import com.items.bim.common.ui.AppTheme
import com.items.bim.common.util.MultiplePermissions
import com.items.bim.common.util.ThreadPoolManager
import com.items.bim.common.util.Utils
import com.items.bim.event.ViewModelEvent
import com.items.bim.viewmodel.HomeViewModel
import com.items.bim.viewmodel.MessagesViewModel
import com.items.bim.viewmodel.UserViewModel
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


private val logger = KotlinLogging.logger {
}


class MainActivity : AppCompatActivity() {

    private lateinit var homeViewModel: HomeViewModel;

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

    private fun initLoad() {
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


    @OptIn(ExperimentalPermissionsApi::class)
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
