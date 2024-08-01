package com.items.bim.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.items.bim.R
import com.items.bim.common.consts.StyleCommon
import com.items.bim.common.consts.SystemApp
import com.items.bim.common.ui.HeadImage
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
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

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
                    Column(modifier =
                    Modifier.padding(start = 10.dp)
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
                    Row(modifier= Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                        .padding(start = 10.dp),
                        horizontalArrangement = Arrangement.End) {
                        Column {
                            Text(text = DateUtils.timestampToDateStr(user.sendDateTime),
                                fontSize = 10.sp,
                                color = Color.Gray)
                            Surface {
                                Text(text = "")
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(DelicateCoroutinesApi::class)
@SuppressLint("ResourceAsColor", "CoroutineCreationDuringComposition")
@Composable
fun MessagesBody(
    messagesProd: () -> SnapshotStateList<MessagesEntity>,
    messagesViewModel: MessagesViewModel,
    recvUserEntity: UserEntity,
    modifier: Modifier,
) {
    val state = MySwipeRefreshState(NORMAL)

    MySwipeRefresh(state = state, onRefresh = { /*TODO*/ }, onLoadMore = { /*TODO*/ },
        modifier = modifier
    ) { refreshModifier ->
        logger.info { "MessagesBody MySwipeRefresh 重组" }
        LazyColumn(refreshModifier, reverseLayout=true) {
            items(messagesProd()) { it ->
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
//                                Text(text = it.sendDateTime, fontSize = 10.sp)
                            Surface(
                                shape = RectangleShape,
                                shadowElevation = 4.dp,
                                tonalElevation = 1.dp,
                                color = Color(R.color.sendMessage),
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .padding(1.dp)
                            ) {
                                Text(
                                    text = it.messageData.substring(
                                        0,
                                        if (it.messageData.length < 20) it.messageData.length else 20
                                    ),
                                    textAlign = TextAlign.Right,
                                    fontSize = 18.sp,
                                    color = Color.White,
                                    modifier = Modifier.padding(4.dp),
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
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
//                                Text(text = it.sendDateTime, fontSize = 10.sp)
                            Surface(
                                shape = RectangleShape,
                                shadowElevation = 4.dp,
                                tonalElevation = 1.dp,
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .padding(1.dp)
                            ) {
                                Text(
                                    text = it.messageData.substring(
                                        0,
                                        if (it.messageData.length < 20) it.messageData.length else 20
                                    ),
                                    textAlign = TextAlign.Left,
                                    fontSize = 18.sp,
                                    modifier = Modifier.padding(5.dp),
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
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
@OptIn(ExperimentalMaterial3Api::class, DelicateCoroutinesApi::class)
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
    var sendData by remember {
        mutableStateOf("")
    }
    val sendDataIsEmpty by remember {
        derivedStateOf { sendData.isEmpty() }
    }
    val messages = remember {
        mutableStateListOf<MessagesEntity>()
    }
    logger.info { "MessagesBody 重组" }
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
                        }, colors = TextFieldDefaults.colors(Color.White),
                        modifier=Modifier.clickable {
                            logger.info { "状态栏高度 ${scrollBehavior.state.heightOffset} " +
                                    "${scrollBehavior.state.heightOffsetLimit}" }
                            scrollBehavior.state.heightOffset =  -scrollBehavior.state.heightOffsetLimit
                        }
                    )
                    if (sendDataIsEmpty) {
                        IconButton(
                            onClick = {
                                mainController.navigate("${PageRouteConfig.IMAGE_SELECTOR}/message")
                            }, modifier = Modifier.size(50.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = "Localized description"
                            )
                        }
                    } else {
                        IconButton(onClick = {
                            // 关闭键盘
//                            activity.dismissKeyboardShortcutsHelper()
                            if (sendData.isNotEmpty()) {
                                scope.launch {
                                    val message = MessagesEntity(
                                        sendUserId = SystemApp.UserId,
                                        sendDateTime = System.currentTimeMillis(),
                                        recvUserId = recvUserEntity.id,
                                        messageData = sendData,
                                        recvDateTime = null,
                                        ack = 0
                                    )
                                    messagesViewModel.saveItem(message)
                                    ThreadPoolManager.getInstance().addTask("message") {
                                        messagesViewModel.messageService.sendMessagesEntity(message)
                                    }
                                    sendData = ""
                                    messages.add(0, message)
                                }
                            }
                        },modifier = Modifier.size(50.dp)) {
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
            .fillMaxHeight(),
    ) { pand ->
        scope.launch(Dispatchers.IO)  {
            val list = messagesViewModel.getMessagesSendAndRecvByUser(
                SystemApp.UserId,
                recvUserEntity.id,
                1,
                100
            )
            messages.addAll(list)
            logger.info { "一直有多少条消息${SystemApp.UserId}:${ recvUserEntity.id}:${list.size}" }
            while (true){
                val mes = messagesViewModel.getMessagesSendAndRecvFlowByUserAck(
                    recvUserEntity.id,
                    SystemApp.UserId, 1, 100
                )
                if (mes.isNotEmpty()){
                    messages.addAll(0, mes)
                    mes.forEach { mess ->
                        // 将未读给确认
                        if (mess.ack == 1){
                            mess.ack = 2
                        }
                    }
                    val num = messagesViewModel.updateItemBatch(mes)
                    Log.d("uiMessageState ", "已确认 $num")
                }
                delay(1.seconds)
            }
        }
        MessagesBody( messagesProd = {messages}, messagesViewModel, recvUserEntity, Modifier.padding(pand))
    }
}