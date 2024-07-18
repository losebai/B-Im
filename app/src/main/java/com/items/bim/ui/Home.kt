package com.items.bim.ui

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.items.bim.AppBase
import com.items.bim.common.ui.LoadingIndicator
import com.items.bim.common.ui.MySwipeRefresh
import com.items.bim.common.ui.MySwipeRefreshState
import com.items.bim.common.ui.NORMAL
import com.items.bim.common.ui.REFRESHING
import com.items.bim.common.util.ThreadPoolManager
import com.items.bim.config.MenuRouteConfig
import com.items.bim.config.PageRouteConfig
import com.items.bim.dto.CommunityEntity
import com.items.bim.viewmodel.CommunityViewModel
import com.items.bim.viewmodel.ImageViewModel
import com.items.bim.viewmodel.LotteryViewModel
import com.items.bim.viewmodel.MessagesViewModel
import com.items.bim.viewmodel.ToolsViewModel
import com.items.bim.viewmodel.UserViewModel
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.launch


@SuppressLint("CoroutineCreationDuringComposition")
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun PageHost(
    appBase: AppBase,
    settingDrawerState: DrawerState,
    imageViewModel: ImageViewModel,
    messagesViewModel: MessagesViewModel,
    mainController: NavHostController,
    communityViewModel: CommunityViewModel,
    userViewModel: UserViewModel,
    toolsViewModel: ToolsViewModel,
    lotteryViewModel: LotteryViewModel,
) {
    var searchUserName by remember {
        mutableStateOf("")
    }
    ModalNavigationDrawer(drawerState = settingDrawerState,
        modifier = Modifier.padding(0.dp),
        drawerContent = {
        ModalDrawerSheet {
            SettingHome(userViewModel.userEntity, mainController)
        }
    }) {
        appBase.Context(content = { innerPadding ->
            val mod = Modifier
                .padding(innerPadding)
                .background(Color.White)
                .fillMaxSize()
            when (appBase.page) {
                MenuRouteConfig.TOOLS_ROUTE -> {
                    ToolsList(
                        toolsViewModel,
                        mod,
                        mainController,
                    )
                }
                MenuRouteConfig.ROUTE_COMMUNITY -> {
                    Community(
                        modifier = Modifier
                            .padding(innerPadding),
                        userViewModel,
                        communityViewModel,
                    )
                }
                MenuRouteConfig.ROUTE_MESSAGE -> {
                    MessagesList(
                        messagesViewModel,
                        userViewModel,
                        mainController,
                        modifier = mod.fillMaxWidth()
                    )
                }
                MenuRouteConfig.ROUTE_USERS -> {
                    Column(modifier = mod) {
                        SearchUser(
                            searchUserName, onValueChange = {
                                searchUserName = it
                            }, modifier = Modifier
                                .height(50.dp)
                                .fillMaxWidth()
                                .padding(2.dp)
                        )
                        UserList(userViewModel, onClick = {
                            userViewModel.recvUserId = it.id
                            mainController.navigate(PageRouteConfig.MESSAGE_ROUTE)
                        })
                    }
                }
            }
        },
            topBar = {
                appBase.GetTopAppBar(userViewModel.userEntity, mainController)
            }, floatingActionButton = {
            })
    }
}


@Composable
fun Community(
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel,
    communityViewModel: CommunityViewModel,
) {
    var list: List<CommunityEntity> by remember {
        mutableStateOf(communityViewModel.getCommunityList())
    }
    ThreadPoolManager.getInstance().addTask("community", "communityList"){
        list = communityViewModel.nextCommunityPage()
    }
    CommunityHome(
        userViewModel.userEntity,
        communityList = list,
        modifier=modifier
    )
}