package com.example.myapplication

import android.content.pm.ActivityInfo
import android.os.Build
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.common.consts.SystemApp
import com.example.myapplication.common.ui.web.WanNavActions
import com.example.myapplication.common.ui.web.WebScreen
import com.example.myapplication.common.util.ThreadPoolManager
import com.example.myapplication.config.MingChaoRoute
import com.example.myapplication.config.PageRouteConfig
import com.example.myapplication.config.WEB_API_ROURE
import com.example.myapplication.entity.toAppUserEntity
import com.example.myapplication.event.GlobalInitEvent
import com.example.myapplication.service.FileService
import com.example.myapplication.ui.EditPage
import com.example.myapplication.ui.GetCookiesUri
import com.example.myapplication.ui.HookList
import com.example.myapplication.ui.ImageGroupList
import com.example.myapplication.ui.ImageSelect
import com.example.myapplication.ui.LotterySimulate
import com.example.myapplication.ui.MCRoleLotteryHome
import com.example.myapplication.ui.MessagesDetail
import com.example.myapplication.ui.PageHost
import com.example.myapplication.ui.PhotoDataSet
import com.example.myapplication.ui.RankingHome
import com.example.myapplication.ui.UserInfoEdit
import com.example.myapplication.viewmodel.CommunityViewModel
import com.example.myapplication.viewmodel.ImageViewModel
import com.example.myapplication.viewmodel.LotteryViewModel
import com.example.myapplication.viewmodel.MessagesViewModel
import com.example.myapplication.viewmodel.ToolsViewModel
import com.example.myapplication.viewmodel.UserViewModel
import com.example.myapplication.viewmodel.WebVIewModel
import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {
}


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun MainNavGraph(
    activity: AppCompatActivity, appBase: AppBase,
    userViewModel: UserViewModel,
    messagesViewModel: MessagesViewModel,
    imageViewModel: ImageViewModel
) {
    val communityViewModel = viewModel<CommunityViewModel>()
    val toolsViewModel = viewModel<ToolsViewModel>()
    val navHostController = rememberNavController()
    val lotteryViewModel = viewModel<LotteryViewModel>()
    val webViewModel = viewModel<WebVIewModel>()
    val wanUiState by webViewModel.uiState.collectAsStateWithLifecycle()
    val webNavActions = remember(navHostController) { WanNavActions(navHostController) }
    GlobalInitEvent.run()
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
                toolsViewModel,
                lotteryViewModel
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
                navHostController,
                Modifier.background(Color.White)
            )
        }
        composable("${PageRouteConfig.IMAGE_SELECTOR}/{event}") { backStackEntry ->
            ImageSelect(imageViewModel, onClose = {
                navHostController.navigateUp()
            },
                onSelect = {
                    when (backStackEntry.arguments?.getString("event")) {
                        "headImage" -> {
                            ThreadPoolManager.getInstance().addTask("init") {
                                val path = FileService.uploadImage(it.filePath)
                                userViewModel.userEntity.imageUrl = FileService.getImageUrl(path)
                                userViewModel.saveUser(
                                    userViewModel.userEntity.toAppUserEntity(
                                        SystemApp.PRODUCT_DEVICE_NUMBER
                                    )
                                )
                                userViewModel.userEntity =
                                    userViewModel.userEntity.copy(imageUrl = userViewModel.userEntity.imageUrl)
                            }
                        }
                    }
                })
        }
        composable(PageRouteConfig.USER_INFO) {
            UserInfoEdit(userViewModel.userEntity, navHostController)
        }
        composable(PageRouteConfig.USER_INFO_USERNAME) {
            EditPage("修改名称", userViewModel.userEntity.name, navHostController) {
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
            EditPage("修改签名", userViewModel.userEntity.note, navHostController) {
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
        composable("${PageRouteConfig.TOOLS_MINGCHAO_LOTTERY_DETAIL}/{id}/{gameName}") { backStackEntry ->
            val gameName = backStackEntry.arguments?.getString("gameName") ?: ""
            LotterySimulate(
                gameName,
                backStackEntry.arguments?.getString("id")?.toLong() ?: SystemApp.UserId,
                lotteryViewModel, navHostController
            )
        }

        composable(MingChaoRoute.BOOK_LIST) {
            HookList(toolsViewModel, navHostController)
        }
//        composable(MingChaoRoute.WIKI) {
//            MCWIKI(MingChaoAPI.WIKI_URL)
//        }
        composable("${WEB_API_ROURE.WEB_ROUTE}/{url}") { backStackEntry ->
            WebScreen(
                originalUrl = backStackEntry.arguments?.getString("url") ?: "",
                webBookmarkData = wanUiState.webBookmarkResult,
                onWebBookmark = { isAdd, text -> webViewModel.onWebBookmark(isAdd, text) },
                onWebHistory = { isAdd, text -> webViewModel.onWebHistory(isAdd, text) },
                onNavigateToBookmarkHistory = { webNavActions.navigateToBookmarkHistory() },
                onNavigateUp = { webNavActions.navigateUp() }
            )
        }
        composable("${MingChaoRoute.SET_COOKIES}/gameName") { backStackEntry ->
            val gameName = backStackEntry.arguments?.getString("gameName") ?: ""
            GetCookiesUri(gameName,
                Modifier,
                toolsViewModel,
                lotteryViewModel,
                onBack = {
                    navHostController.navigateUp()
                })
        }
        composable(PageRouteConfig.TOOLS_IMAGE_LIST) {

        }
        composable("${MingChaoRoute.LOTTERY_ROUTE}/{gameName}") { baseEntity ->
            val gameName = baseEntity.arguments?.getString("gameName") ?: ""
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            // 全屏并隐藏状态栏
//            activity.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
//            WindowCompat.setDecorFitsSystemWindows(activity.window, false)
            activity.enableEdgeToEdge()
            MCRoleLotteryHome(
                gameName,
                lotteryViewModel, onLottery = { pool, num ->
                    val isUp = 5 >= pool.poolType.value && pool.poolType.value <= 6
                    val catalogueId = if (pool.poolType.value % 2 == 1) 1105 else 1106
                    val poolId = pool.poolId
                    ThreadPoolManager.getInstance().addTask("lottery", "lottery") {
                        logger.info { "开始抽奖" }
                        lotteryViewModel.award = lotteryViewModel.randomAward(
                            gameName, catalogueId, poolId, num, isUp
                        )
                    }
                    navHostController.navigate(MingChaoRoute.AWARD_LIST)
                }, onDispatch = {
                    activity.enableEdgeToEdge()
                    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
                    navHostController.navigateUp()
                })
        }
        composable(PageRouteConfig.RANKING_HOME) {
            RankingHome(toolsViewModel, navHostController)
        }
    }
}
