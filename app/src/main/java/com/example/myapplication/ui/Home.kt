package com.example.myapplication.ui

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.myapplication.AppBase
import com.example.myapplication.common.ui.LoadingIndicator
import com.example.myapplication.common.ui.MySwipeRefresh
import com.example.myapplication.common.ui.MySwipeRefreshState
import com.example.myapplication.common.ui.NORMAL
import com.example.myapplication.common.ui.REFRESHING
import com.example.myapplication.common.util.ThreadPoolManager
import com.example.myapplication.config.MenuRouteConfig
import com.example.myapplication.config.PageRouteConfig
import com.example.myapplication.dto.CommunityEntity
import com.example.myapplication.viewmodel.CommunityViewModel
import com.example.myapplication.viewmodel.ImageViewModel
import com.example.myapplication.viewmodel.LotteryViewModel
import com.example.myapplication.viewmodel.MessagesViewModel
import com.example.myapplication.viewmodel.ToolsViewModel
import com.example.myapplication.viewmodel.UserViewModel
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.launch


private val logger = KotlinLogging.logger {
}

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
                    appBase.topVisible = false
                    ToolsList(
                        toolsViewModel,
                        mod,
                        mainController,
                    )
                }
                MenuRouteConfig.ROUTE_COMMUNITY -> {
                    appBase.topVisible = false
                    Community(
                        modifier = Modifier
                            .padding(innerPadding),
                        userViewModel,
                        communityViewModel,
                    )
                }
                MenuRouteConfig.ROUTE_MESSAGE -> {
                    appBase.topVisible = true
                    MessagesList(
                        messagesViewModel,
                        userViewModel,
                        mainController,
                        modifier = mod.fillMaxWidth()
                    )
                }
                MenuRouteConfig.ROUTE_USERS -> {
                    appBase.topVisible = true
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
    val state = MySwipeRefreshState(NORMAL)
    val scope = rememberCoroutineScope()
    var list: List<CommunityEntity> by remember {
        mutableStateOf(communityViewModel.getCommunityList())
    }
    ThreadPoolManager.getInstance().addTask("community", "communityList"){
        list = communityViewModel.nextCommunityPage()
    }
    MySwipeRefresh(
        state = state,
        indicator = { _modifier, s, indicatorHeight ->
            LoadingIndicator(_modifier, s, indicatorHeight)
        },
        onRefresh = {
            scope.launch {
                state.loadState = REFRESHING
                communityViewModel.clearCommunityList()
                ThreadPoolManager.getInstance().addTask("community", "communityList") {
                    list = communityViewModel.nextCommunityPage()
                }
                logger.info { "社区下拉刷新" }
                state.loadState = NORMAL
            }
        },
        onLoadMore = {
//                scope.launch {
//                    state.loadState = LOADING_MORE
//                    ThreadPoolManager.getInstance().addTask("community") {
//                        list = communityViewModel.nextCommunityPage()
//                    }
//                    logger.info { "社区上拉刷新" }
//                    state.loadState = NORMAL
//                }
        },
        modifier = modifier
    ) { _modifier ->
        CommunityHome(
            userViewModel.userEntity,
            communityList = list,
            modifier = _modifier
        )
    }
}