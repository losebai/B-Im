package com.items.bim.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.items.bim.common.consts.StyleCommon
import com.items.bim.common.consts.SystemApp
import com.items.bim.common.ui.HeadImage
import com.items.bim.common.ui.InputBottom
import com.items.bim.common.ui.MessageText
import com.items.bim.common.ui.MySwipeRefresh
import com.items.bim.common.ui.MySwipeRefreshState
import com.items.bim.common.ui.NORMAL
import com.items.bim.common.ui.REFRESHING
import com.items.bim.common.util.DateUtils
import com.items.bim.common.util.ThreadPoolManager
import com.items.bim.config.PageRouteConfig
import com.items.bim.entity.MessagesEntity
import com.items.bim.entity.UserEntity
import com.items.bim.viewmodel.MessagesViewModel
import com.items.bim.viewmodel.UserViewModel
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

private val logger = KotlinLogging.logger {
}


/**
 * 消息列表
 * @param [messagesViewModel]
 * @param [modifier]
 */
@Composable
fun MessagesList(
    messagesViewModel: MessagesViewModel,
    userViewModel: UserViewModel,
    mainController: NavHostController,
    modifier: Modifier
) {
    val state = MySwipeRefreshState(NORMAL)
    MySwipeRefresh(
        state = state,
        onRefresh = {
            ThreadPoolManager.getInstance().addTask("MessagesList") {
                state.loadState = REFRESHING
                logger.info { "正在获取刷新 消息列表" }
                state.loadState = NORMAL
            }
        },
        onLoadMore = {
            state.loadState = REFRESHING
        },
        modifier = modifier
    ) {
        LazyColumn(it.padding(10.dp)) {
            items(messagesViewModel.userMessagesList) { user ->
                val isSend = SystemApp.UserId == user.sendUserId
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .clickable {
                        userViewModel.recvUserId = if (isSend) user.recvUserId else user.sendUserId
                        mainController.navigate(PageRouteConfig.MESSAGE_ROUTE)
                    }) {
                    HeadImage(
                        if (isSend) user.recvUserImageUri else user.sendUserImageUri,
                        modifier = StyleCommon.HEAD_IMAGE
                    ) {
                    }
                    Column(
                        modifier =
                        Modifier
                            .padding(start = 10.dp)
                            .fillMaxWidth(0.8f)
                    ) {
                        Text(
                            text = if (isSend) user.recvUserName else user.sendUserName,
                            modifier = Modifier
                                .fillMaxWidth(),
                            color = Color.Black, fontSize = 20.sp
                        )
                        Text(
                            text = user.messageData.substring(
                                0,
                                if (user.messageData.length < 20) user.messageData.length else 20
                            ),
                            fontSize = 14.sp,
                            color = Color.Black
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp)
                            .padding(start = 10.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Column {
                            Text(
                                text = DateUtils.timestampToDateStr(user.sendDateTime),
                                fontSize = 10.sp,
                                color = Color.Gray
                            )
                            if (user.num!! > 0) {
                                Surface(modifier = Modifier.background(Color.Red)) {
                                    Text(text = "${user.num}", fontSize = 20.sp)
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}

@SuppressLint("ResourceAsColor", "CoroutineCreationDuringComposition")
@Composable
fun MessagesBody(
    messagesProd: () -> SnapshotStateList<MessagesEntity>,
    messagesViewModel: MessagesViewModel,
    recvUserEntity: UserEntity,
    modifier: Modifier,
) {
    val state = MySwipeRefreshState(NORMAL)
    val scope = rememberCoroutineScope()
    MySwipeRefresh(state = state, onRefresh = { /*TODO*/ }, onLoadMore = { /*TODO*/ },
        modifier = modifier
    ) { refreshModifier ->
        logger.info { "MessagesBody MySwipeRefresh 重组" }
        LazyColumn(modifier, reverseLayout = true) {
            val messages = messagesProd()
            items(messages) { it ->
                val isSend = it.sendUserId == SystemApp.UserId
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = if (isSend) Arrangement.End
                    else Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    if (isSend) {
                        Column(
                            modifier = Modifier.padding(10.dp),
                            horizontalAlignment = Alignment.End
                        ) {
                            MessageText(it)
                        }
                        HeadImage(
                            SystemApp.USER_IMAGE,
                            modifier = StyleCommon.HEAD_IMAGE
                        ) {
                        }
                    } else {
                        HeadImage(recvUserEntity.imageUrl, modifier = StyleCommon.HEAD_IMAGE) {
                        }
                        Column(modifier = Modifier.padding(10.dp)) {
                            MessageText(it)
                        }
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
    val scope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val messages = remember {
        mutableStateListOf<MessagesEntity>()
    }
    LaunchedEffect(key1 = recvUserEntity.id) {
        messages.clear()
        scope.launch(Dispatchers.Default) {
            messages.addAll(
                messagesViewModel.getMessagesSendAndRecvByUser(
                    SystemApp.UserId,
                    recvUserEntity.id,
                    1,
                    10
                )
            )
        }
    }
    logger.info { "MessagesBody 重组 $recvUserEntity" }
    Scaffold(
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
                        scope.cancel()
                        messagesViewModel.isOnUserMessageLister = false
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
            InputBottom(modifier, onSendData = {
                val message = MessagesEntity(
                    sendUserId = SystemApp.UserId,
                    sendDateTime = System.currentTimeMillis(),
                    recvUserId = recvUserEntity.id,
                    messageData = it,
                    recvDateTime = null,
                    ack = if (recvUserEntity.id == SystemApp.UserId) 5 else 1
                )
                messagesViewModel.sendUserMessage(message)
                messages.add(0, message)
            }, onCloseSendData = {
                mainController.navigate("${PageRouteConfig.IMAGE_SELECTOR}/message")
            })
        },
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(),
    ) { pand ->
        if (!messagesViewModel.isOnUserMessageLister) {
            scope.launch {
                messagesViewModel.onUserMessageLister(this, recvUserEntity.id) {
                    messages.add(0, it)
                    logger.debug { "onUserMessageLister ${it}" }
                }
            }
            messagesViewModel.isOnUserMessageLister = true
        }
        MessagesBody(
            messagesProd = { messages },
            messagesViewModel,
            recvUserEntity,
            Modifier.padding(pand)
        )
    }
}