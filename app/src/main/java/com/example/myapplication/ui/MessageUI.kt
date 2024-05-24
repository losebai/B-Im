package com.example.myapplication.ui

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.R
import com.example.myapplication.common.consts.StyleCommon
import com.example.myapplication.common.consts.SystemApp
import com.example.myapplication.common.ui.HeadImage
import com.example.myapplication.common.ui.MySwipeRefresh
import com.example.myapplication.common.ui.MySwipeRefreshState
import com.example.myapplication.common.ui.NORMAL
import com.example.myapplication.common.util.Utils
import com.example.myapplication.config.PageRouteConfig
import com.example.myapplication.entity.UserMessages
import com.example.myapplication.entity.MessagesEntity
import com.example.myapplication.entity.UserEntity
import com.example.myapplication.viewmodel.ImageViewModel
import com.example.myapplication.viewmodel.MessagesViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime


@Composable
fun MessagesList(messages: List<UserMessages>, modifier: Modifier) {
    val state = MySwipeRefreshState(NORMAL)
    MySwipeRefresh(state = state, onRefresh = { /*TODO*/ }, onLoadMore = { /*TODO*/ },
        modifier = modifier
    ) {
        LazyColumn(it) {
            items(messages) {
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

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "ResourceAsColor")
@Composable
fun MessagesDetail(
    sendUserEntity: UserEntity,
    messagesViewModel: MessagesViewModel,
    messages: MutableList<MessagesEntity>,
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
                        sendUserEntity.name,
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
            var isAdd by remember {
                mutableStateOf(true)
            }
            BottomAppBar(modifier= modifier.background(MaterialTheme.colorScheme.background)){
                Row(
                    modifier = modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    verticalAlignment=Alignment.CenterVertically
                ) {
                    TextField(value = sendData, onValueChange = {
                        sendData = it
                        if (it.isEmpty()){
                            isAdd = true
                        }
                    }, modifier = Modifier.fillMaxWidth(0.8f))
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                imageVector = Icons.Filled.FavoriteBorder,
                                contentDescription = "Localized description"
                            )
                        }
                    if (isAdd) {
                        IconButton(
                            onClick = {
                                isAdd = false
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
                            if (sendData.isNotEmpty()){
                                scope.launch {
                                    messagesViewModel.saveItem(
                                        MessagesEntity(
                                            sendUserId = SystemApp.UserId,
                                            sendDateTime = Utils.localDateTimeToString(LocalDateTime.now()),
                                            recvUserId = sendUserEntity.id,
                                            messageData = sendData,
                                            recvDateTime = null,
                                            ack = 0
                                        )
                                    )
                                }
                            }else{
                                isAdd = true
                            }
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
            .background(MaterialTheme.colorScheme.background),
    ) { pandding ->
        MySwipeRefresh(state = state, onRefresh = { /*TODO*/ }, onLoadMore = { /*TODO*/ },
            modifier = Modifier.padding(pandding)
        ) { refreshModifier ->
            LazyColumn(refreshModifier) {
                items(messages) {
                    Row(
                        horizontalArrangement = if (it.sendUserId == SystemApp.UserId) Arrangement.End
                        else Arrangement.Start
                    ) {
                        HeadImage(sendUserEntity.imageUrl, modifier = StyleCommon.HEAD_IMAGE) {
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