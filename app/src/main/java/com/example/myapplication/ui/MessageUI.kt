package com.example.myapplication.ui

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.common.consts.StyleCommon
import com.example.myapplication.common.consts.SystemApp
import com.example.myapplication.common.ui.HeadImage
import com.example.myapplication.common.ui.MySwipeRefresh
import com.example.myapplication.common.ui.MySwipeRefreshState
import com.example.myapplication.common.ui.NORMAL
import com.example.myapplication.common.ui.REFRESHING
import com.example.myapplication.common.util.ThreadPoolManager
import com.example.myapplication.common.util.Utils
import com.example.myapplication.config.PageRouteConfig
import com.example.myapplication.entity.UserMessages
import com.example.myapplication.entity.MessagesEntity
import com.example.myapplication.entity.UserEntity
import com.example.myapplication.viewmodel.MessagesViewModel
import kotlinx.coroutines.launch
import mu.KotlinLogging
import java.time.LocalDateTime



private val logger = KotlinLogging.logger {}
/**
 * 消息列表
 * @param [messagesViewModel]
 * @param [modifier]
 */
@Composable
fun MessagesList(messagesViewModel: MessagesViewModel, modifier: Modifier) {
    val state = MySwipeRefreshState(NORMAL)
    val scope = rememberCoroutineScope()
    MySwipeRefresh(
        state = state,
        onRefresh = {
            ThreadPoolManager.getInstance().addTask("MessagesList"){
                state.loadState = REFRESHING

                logger.info { "正在获取刷新联系人列表" }
                state.loadState = NORMAL
            }
        },
        onLoadMore = {
            state.loadState = REFRESHING
        },
        modifier = modifier
    ) {
        LazyColumn(it.padding(10.dp)) {
            items(messagesViewModel.userMessagesList) {
                Row {
                    HeadImage(it.sendUserImageUri, modifier = StyleCommon.HEAD_IMAGE) {
                    }
                    Row {
                        Text(text = it.sendUserName, fontSize = 18.sp)
                        Text(
                            text = it.messageData.substring(
                                0,
                                if (it.messageData.length < 20) it.messageData.length else 20
                            )
                        )
                    }
                }
            }
        }
    }
}

/**
 * 和用户对话窗口
 * @param [recvUserEntity]
 * @param [messagesViewModel]
 * @param [mainController]
 * @param [modifier]
 */
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint(
    "UnusedMaterial3ScaffoldPaddingParameter", "ResourceAsColor",
    "CoroutineCreationDuringComposition"
)
@Composable
fun MessagesDetail(
    recvUserEntity: UserEntity,
    messagesViewModel: MessagesViewModel,
    mainController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val activity = LocalContext.current as Activity
    val state = MySwipeRefreshState(NORMAL)
    val scope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    var sendData by remember {
        mutableStateOf("")
    }
    val messages = remember {
        mutableListOf<MessagesEntity>()
    }
    scope.launch {
        messagesViewModel.getMessagesSendAndRecvByUser(SystemApp.UserId, recvUserEntity.id, 1, 10)
            .collect {
                messages.addAll(it)
            }
    }
    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = SystemApp.snackBarHostState,
                modifier = Modifier.padding(0.dp)
            )
        },
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        recvUserEntity.name,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                // 返回
                navigationIcon = {
                    IconButton(onClick = {
                        // 从列表页返回
                        mainController.navigateUp()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Localized description"
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
        bottomBar = {
            BottomAppBar(modifier = modifier) {
                Row(
                    modifier = modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = sendData, onValueChange = {
                            sendData = it
                        }, modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .background(Color.White)
                    )
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Filled.FavoriteBorder,
                            contentDescription = "Localized description"
                        )
                    }
                    if (sendData.isEmpty()) {
                        IconButton(
                            onClick = {
                                mainController.navigate(PageRouteConfig.IMAGE_SELECTOR)
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = "Localized description"
                            )
                        }
                    } else {
                        IconButton(onClick = {
                            // 关闭键盘
                            activity.dismissKeyboardShortcutsHelper()
                            if (sendData.isNotEmpty()) {
                                scope.launch {
                                    messagesViewModel.saveItem(
                                        MessagesEntity(
                                            sendUserId = SystemApp.UserId,
                                            sendDateTime = Utils.localDateTimeToString(LocalDateTime.now()),
                                            recvUserId = recvUserEntity.id,
                                            messageData = sendData,
                                            recvDateTime = null,
                                            ack = 0
                                        )
                                    )
                                }
                            }
                            sendData = ""
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Send,
                                contentDescription = "Localized description"
                            )
                        }
                    }
                }
            }

        },
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.White),
    ) { pandding ->
        MySwipeRefresh(state = state, onRefresh = { /*TODO*/ }, onLoadMore = { /*TODO*/ },
            modifier = Modifier
                .padding(pandding)
                .background(Color.White)
        ) { refreshModifier ->
            LazyColumn(refreshModifier, reverseLayout = true) {
                items(messages) {
                    Row(
                        horizontalArrangement = if (it.sendUserId == SystemApp.UserId) Arrangement.End
                        else Arrangement.Start
                    ) {
                        HeadImage(recvUserEntity.imageUrl, modifier = StyleCommon.HEAD_IMAGE) {
                        }
                        Row {
                            Text(
                                text = it.messageData.substring(
                                    0,
                                    if (it.messageData.length < 20) it.messageData.length else 20
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}