package com.items.bim

import android.content.pm.ActivityInfo
import android.os.Build
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
import com.items.bim.common.consts.SystemApp
import com.items.bim.common.ui.web.WanNavActions
import com.items.bim.common.ui.web.WebScreen
import com.items.bim.common.util.ThreadPoolManager
import com.items.bim.config.MingChaoRoute
import com.items.bim.config.PageRouteConfig
import com.items.bim.config.WEB_API_ROURE
import com.items.bim.dto.AppDynamic
import com.items.bim.entity.toAppUserEntity
import com.items.bim.event.GlobalInitEvent
import com.items.bim.service.FileService
import com.items.bim.ui.AddDynamic
import com.items.bim.ui.EditPage
import com.items.bim.ui.GameRoleRaking
import com.items.bim.ui.GetCookiesUri
import com.items.bim.ui.HookList
import com.items.bim.ui.ImageGroupList
import com.items.bim.ui.ImageSelect
import com.items.bim.ui.LotterySimulate
import com.items.bim.ui.MCRoleLotteryHome
import com.items.bim.ui.MessagesDetail
import com.items.bim.ui.PageHost
import com.items.bim.ui.PhotoDataSet
import com.items.bim.ui.RankingHome
import com.items.bim.ui.UserInfoEdit
import com.items.bim.viewmodel.CommunityViewModel
import com.items.bim.viewmodel.ConfigViewModel
import com.items.bim.viewmodel.ImageViewModel
import com.items.bim.viewmodel.LotteryViewModel
import com.items.bim.viewmodel.MessagesViewModel
import com.items.bim.viewmodel.ToolsViewModel
import com.items.bim.viewmodel.UserViewModel
import com.items.bim.viewmodel.WebVIewModel
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlin.concurrent.thread

private val logger = KotlinLogging.logger {
}


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun MainNavGraph(
    activity: AppCompatActivity,
    appBase: AppBase,
    userViewModel: UserViewModel,
    messagesViewModel: MessagesViewModel,
    imageViewModel: ImageViewModel,
    init: () -> Unit = {}
) {
    val configViewModel = viewModel<ConfigViewModel>()
    val communityViewModel = viewModel<CommunityViewModel>()
    val toolsViewModel = viewModel<ToolsViewModel>()
    val navHostController = rememberNavController()
    val lotteryViewModel = viewModel<LotteryViewModel>()
    val webViewModel = viewModel<WebVIewModel>()
    val wanUiState by webViewModel.uiState.collectAsStateWithLifecycle()
    val webNavActions = remember(navHostController) { WanNavActions(navHostController) }
    thread {
        GlobalInitEvent.run()
        init()
        configViewModel.check()
    }
    NavHost(
        navController = navHostController,
        startDestination = PageRouteConfig.MENU_ROUTE,
        enterTransition = {
            slideInHorizontally(animationSpec = tween(800), //动画时长1s
                initialOffsetX = {
                    -it //初始位置在负一屏的位置，也就是说初始位置我们看不到，动画动起来的时候会从负一屏位置滑动到屏幕位置
                })
        },
        exitTransition = {
            slideOutHorizontally(animationSpec = tween(800), targetOffsetX = {
                it
            })
        },
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
            logger.debug { PageRouteConfig.IMAGE_PAGE_ROUTE }
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
                        "addDynamic" -> {
                            ThreadPoolManager.getInstance().addTask("init", "addDynamic") {
                                val path = FileService.uploadImage(it.filePath)
                                communityViewModel.images.add(FileService.getImageUrl(path))
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
                lotteryViewModel,
                toolsViewModel, navHostController
            )
        }

        composable(MingChaoRoute.BOOK_LIST) {
            HookList(toolsViewModel, navHostController)
        }
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
        composable("${MingChaoRoute.SET_COOKIES}/{gameName}") { backStackEntry ->
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
        composable("${PageRouteConfig.RANKING_HOME}/{gameName}") { baseEntity ->
            val gameName = baseEntity.arguments?.getString("gameName") ?: ""
            RankingHome(gameNameValue = {
                gameName
            }, toolsViewModel, navHostController)
        }
        composable("${PageRouteConfig.TOOLS_GAME_ROLE_RAKING}/{gameName}") { baseEntity ->
            val gameName = baseEntity.arguments?.getString("gameName") ?: ""
            GameRoleRaking(gameName, toolsViewModel, configViewModel, navHostController)
        }
        composable(PageRouteConfig.ADD_DYNAMIC){
            AddDynamic(mainController = navHostController, communityViewModel)
        }
    }
}