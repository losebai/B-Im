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
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.items.bim.AppGetBottomBar
import com.items.bim.Context
import com.items.bim.AppGetTopAppBar
import com.items.bim.common.util.ThreadPoolManager
import com.items.bim.config.MenuRouteConfig
import com.items.bim.config.PageRouteConfig
import com.items.bim.dto.CommunityEntity
import com.items.bim.viewmodel.CommunityViewModel
import com.items.bim.viewmodel.HomeViewModel
import com.items.bim.viewmodel.ImageViewModel
import com.items.bim.viewmodel.LotteryViewModel
import com.items.bim.viewmodel.MessagesViewModel
import com.items.bim.viewmodel.ToolsViewModel
import com.items.bim.viewmodel.UserViewModel


@SuppressLint("CoroutineCreationDuringComposition")
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun PageHost(
    homeViewModel: HomeViewModel,
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
    ModalNavigationDrawer(drawerState = homeViewModel.settingDrawerState,
        modifier = Modifier.padding(0.dp),
        drawerContent = {
        ModalDrawerSheet {
            SettingHome(userViewModel.userEntity, mainController)
        }
    }) {
        Context(content = { innerPadding ->
            val mod = Modifier
                .padding(innerPadding)
                .background(Color.White)
                .fillMaxSize()
            when (homeViewModel.page) {
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
                        mainController
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
                AppGetTopAppBar(userViewModel, mainController, homeViewModel)
            },
            bottomBar = {
                AppGetBottomBar(homeViewModel, userViewModel)
            },
            floatingActionButton = {
            })
    }
}


@Composable
fun Community(
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel,
    communityViewModel: CommunityViewModel,
    mainController: NavHostController
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
        modifier=modifier,
        mainController=mainController
    )
}