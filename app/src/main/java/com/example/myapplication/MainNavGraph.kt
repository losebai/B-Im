package com.example.myapplication

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.common.consts.AppAPI
import com.example.myapplication.common.consts.SystemApp
import com.example.myapplication.common.util.ThreadPoolManager
import com.example.myapplication.config.MingChaoRoute
import com.example.myapplication.config.PageRouteConfig
import com.example.myapplication.entity.toAppUserEntity
import com.example.myapplication.ui.HookList
import com.example.myapplication.ui.ImageGroupList
import com.example.myapplication.ui.ImageSelect
import com.example.myapplication.ui.LotterySimulate
import com.example.myapplication.ui.MCWIKI
import com.example.myapplication.ui.MessagesDetail
import com.example.myapplication.ui.PageHost
import com.example.myapplication.ui.PhotoDataSet
import com.example.myapplication.ui.UserInfoUI
import com.example.myapplication.viewmodel.CommunityViewModel
import com.example.myapplication.viewmodel.ImageViewModel
import com.example.myapplication.viewmodel.MessagesViewModel
import com.example.myapplication.viewmodel.ToolsViewModel
import com.example.myapplication.viewmodel.UserViewModel


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun MainNavGraph(appBase: AppBase,
    userViewModel: UserViewModel,
                 messagesViewModel: MessagesViewModel,
                 init : ()-> Unit){
    val imageViewModel = viewModel<ImageViewModel>()
    val communityViewModel = viewModel<CommunityViewModel>()
    val toolsViewModel = viewModel<ToolsViewModel>()
    val navHostController = rememberNavController()
    try {
        init()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    NavHost(
        navController = navHostController,
        startDestination = PageRouteConfig.MENU_ROUTE,
        modifier = Modifier
            .fillMaxWidth(1f)
            .fillMaxHeight(1f)
    ) {
        // 一级页面
        composable(PageRouteConfig.MENU_ROUTE) {
            PageHost(
                appBase,
                appBase.settingDrawerState,
                imageViewModel,
                messagesViewModel,
                navHostController,
                communityViewModel,
                userViewModel,
                toolsViewModel
            )
        }
        // 二级页面 相片页
        composable(PageRouteConfig.IMAGE_PAGE_ROUTE) {
            PhotoDataSet(imageViewModel, navHostController)
        }
        composable(PageRouteConfig.IMAGE_GROUP_LIST) {
            ImageGroupList(
                imageViewModel,
                navHostController
            )
        }
        composable(PageRouteConfig.MESSAGE_ROUTE) {
            MessagesDetail(
                userViewModel.getLocalUserById(userViewModel.recvUserId),
                messagesViewModel,
                appBase.navHostController,
                Modifier.background(Color.White)
            )
        }
        composable(PageRouteConfig.IMAGE_SELECTOR) {
            ImageSelect(imageViewModel) {
                navHostController.navigateUp()
            }
        }
        composable(PageRouteConfig.USER_INFO) {
            UserInfoUI.UserInfoEdit(userViewModel.userEntity, navHostController)
        }
        composable(PageRouteConfig.USER_INFO_USERNAME) {
            UserInfoUI.EditPage("修改名称", navHostController) {
                userViewModel.userEntity = userViewModel.userEntity.apply {
                    name = it
                }
                ThreadPoolManager.getInstance().addTask("init") {
                    userViewModel.saveUser(
                        userViewModel.userEntity.toAppUserEntity(
                            SystemApp.PRODUCT_DEVICE_NUMBER
                        )
                    )
                }
            }
        }
        composable(PageRouteConfig.USER_INFO_NOTE) {
            UserInfoUI.EditPage("修改签名", navHostController) {
                userViewModel.userEntity = userViewModel.userEntity.apply {
                    note = it
                }
            }
            ThreadPoolManager.getInstance().addTask("init") {
                userViewModel.saveUser(
                    userViewModel.userEntity.toAppUserEntity(
                        SystemApp.PRODUCT_DEVICE_NUMBER
                    )
                )
            }
        }
        composable(PageRouteConfig.TOOLS_MINGCHAO_LOTTERY_DETAIL) {
            LotterySimulate(toolsViewModel.lotteryMap, navHostController)
        }

        composable(MingChaoRoute.BOOK_LIST){
            HookList(toolsViewModel, navHostController)
        }
        composable(MingChaoRoute.WIKI){
            MCWIKI(AppAPI.MingChao.WIKI_URL)
        }
    }
}
