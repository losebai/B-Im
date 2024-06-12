package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.Coil
import coil.ImageLoader
import com.example.myapplication.common.consts.SystemApp
import com.example.myapplication.common.ui.LOADING_MORE
import com.example.myapplication.common.ui.LoadingIndicator
import com.example.myapplication.common.ui.MySwipeRefresh
import com.example.myapplication.common.ui.MySwipeRefreshState
import com.example.myapplication.common.ui.NORMAL
import com.example.myapplication.common.ui.REFRESHING
import com.example.myapplication.common.util.ThreadPoolManager
import com.example.myapplication.common.util.Utils
import com.example.myapplication.config.MenuRouteConfig
import com.example.myapplication.config.MingChaoRoute
import com.example.myapplication.config.PageRouteConfig
import com.example.myapplication.dto.CommunityEntity
import com.example.myapplication.entity.UserEntity
import com.example.myapplication.entity.toAppUserEntity
import com.example.myapplication.event.ViewModelEvent
import com.example.myapplication.remote.entity.AppUserEntity
import com.example.myapplication.remote.entity.toUserEntity
import com.example.myapplication.ui.AppTheme
import com.example.myapplication.ui.CommunityHome
import com.example.myapplication.ui.HookList
import com.example.myapplication.ui.ImageGroupList
import com.example.myapplication.ui.ImageSelect
import com.example.myapplication.ui.LotterySimulate
import com.example.myapplication.ui.MessagesDetail
import com.example.myapplication.ui.MessagesList
import com.example.myapplication.ui.MingChaoHome
import com.example.myapplication.ui.PhotoDataSet
import com.example.myapplication.ui.SearchUser
import com.example.myapplication.ui.SettingHome
import com.example.myapplication.ui.ToolsUI
import com.example.myapplication.ui.ToolsUI.ToolsList
import com.example.myapplication.ui.UserInfoUI.EditPage
import com.example.myapplication.ui.UserInfoUI.UserInfoEdit
import com.example.myapplication.ui.UserList
import com.example.myapplication.viewmodel.CommunityViewModel
import com.example.myapplication.viewmodel.ImageViewModel
import com.example.myapplication.viewmodel.MessagesViewModel
import com.example.myapplication.viewmodel.ToolsViewModel
import com.example.myapplication.viewmodel.UserViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import io.github.oshai.kotlinlogging.KotlinLogging
import org.noear.socketd.utils.RunUtils
import java.util.stream.Collectors


private val logger = KotlinLogging.logger {
}


class MainActivity : AppCompatActivity() {


    private var appBase: AppBase = AppBase()

    private lateinit var userViewModel: UserViewModel;

    private lateinit var communityViewModel: CommunityViewModel;

    private lateinit var messagesViewModel: MessagesViewModel;

    private lateinit var toolsViewModel: ToolsViewModel;

    private val viewModelEvent: ViewModelEvent = ViewModelEvent.getInstance(this)


    override fun finish() {
        super.finish()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStop() {
        messagesViewModel.messageService.close()
        super.onStop()
    }

    private fun init() {
        Coil.setImageLoader(ImageLoader(this))
        // 初始化的时候保存和更新
        // 默认账户信息
        ThreadPoolManager.getInstance().addTask("init") {
            val appUserEntity = Utils.randomUser()
            appUserEntity.deviceNumber = SystemApp.PRODUCT_DEVICE_NUMBER
            try {
                val user = userViewModel.gerUserByNumber(SystemApp.PRODUCT_DEVICE_NUMBER)
                if (user.id != 0L) {
                    SystemApp.UserId = user.id
                    SystemApp.USER_IMAGE = user.imageUrl
                    appUserEntity.id = user.id
                    userViewModel.userEntity = user.toUserEntity()
                } else {
                    userViewModel.saveUser(appUserEntity)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            logger.info { "开始加载联系人" }
            val users = userViewModel.getReferUser(AppUserEntity())
            val map = users.parallelStream().collect(Collectors.toMap(UserEntity::id) { it })
            userViewModel.users = users
            userViewModel.userMap = map
            communityViewModel.nextCommunityPage()
            appBase.imageViewModel.getDay7Images(this)
            messagesViewModel.messageService.sendText("", "")
//            GlobalScope.launch(Dispatchers.Default)  {
//                // 监听联系人列表
//                messagesViewModel.getUserMessageLastByUserId(
//                    SystemApp.UserId,
//                    SystemApp.UserId,
//                ){
//                    messagesViewModel.userMessagesList.clear()
//                    messagesViewModel.userMessagesList.addAll(it)
//                    logger.info { "消息列表:${it.size}" }
//                }
//            }
        }
        MainScope().launch {
            viewModelEvent.onUserMessageLastByUserId(
                this@MainActivity,
                SystemApp.UserId,
                SystemApp.UserId,
                userViewModel
            ) { userMessages ->
                if (userMessages.isNotEmpty()) {
                    messagesViewModel.userMessagesList.clear()
                    messagesViewModel.userMessagesList.addAll(userMessages)
                }
            }
            viewModelEvent.onUserAll(this@MainActivity, userViewModel)
            Utils.message(this, "程序初始化完成", SystemApp.snackBarHostState)
        }
    }


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userViewModel = ViewModelProvider(
            this,
            UserViewModel.MessageViewModelFactory(this)
        )[UserViewModel::class.java]
        messagesViewModel =
            ViewModelProvider(
                this,
                MessagesViewModel.MessageViewModelFactory(this)
            )[MessagesViewModel::class.java]
        setContent {
            AppTheme(appBase.darkTheme) {
                appBase.imageViewModel = viewModel<ImageViewModel>()
                communityViewModel = viewModel<CommunityViewModel>()
                toolsViewModel = viewModel()
                appBase.navHostController = rememberNavController()
                try {
                    this.init()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                NavHost(
                    navController = appBase.navHostController,
                    startDestination = PageRouteConfig.MENU_ROUTE,
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .fillMaxHeight(1f)
                ) {
                    // 一级页面
                    composable(PageRouteConfig.MENU_ROUTE) {
                        PageHost(
                            appBase.imageViewModel,
                            messagesViewModel,
                            appBase.navHostController,
                            communityViewModel,
                            userViewModel
                        )
                    }
                    // 二级页面 相片页
                    composable(PageRouteConfig.IMAGE_PAGE_ROUTE) {
                        PhotoDataSet(appBase.imageViewModel, appBase.navHostController)
                    }
                    composable(PageRouteConfig.IMAGE_GROUP_LIST) {
                        ImageGroupList(
                            appBase.imageViewModel,
                            appBase.navHostController
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
                        ImageSelect(appBase.imageViewModel) {
                            appBase.navHostController.navigateUp()
                        }
                    }
                    composable(PageRouteConfig.USER_INFO) {
                        UserInfoEdit(userViewModel.userEntity, appBase.navHostController)
                    }
                    composable(PageRouteConfig.USER_INFO_USERNAME) {
                        EditPage("修改名称", appBase.navHostController) {
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
                        EditPage("修改签名", appBase.navHostController) {
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
                        LotterySimulate(toolsViewModel.lotteryMap, appBase.navHostController)
                    }

                    composable(MingChaoRoute.BOOK_LIST){
                        HookList(toolsViewModel, appBase.navHostController)
                    }
                }
            }
        }
    }

    @SuppressLint("CoroutineCreationDuringComposition")
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @Composable
    fun PageHost(
        imageViewModel: ImageViewModel,
        messagesViewModel: MessagesViewModel,
        mainController: NavHostController,
        communityViewModel: CommunityViewModel,
        userViewModel: UserViewModel
    ) {
        var searchUserName by remember {
            mutableStateOf("")
        }
        ModalNavigationDrawer(drawerState = appBase.settingDrawerState, drawerContent = {
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
                            appBase.navHostController,
                        )
                    }

                    MenuRouteConfig.ROUTE_COMMUNITY -> {
                        appBase.topVisible = false
                        Community(
                            communityViewModel, modifier = Modifier
                                .padding(innerPadding)
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
                    appBase.GetTopAppBar(userViewModel.userEntity)
                }, floatingActionButton = {
                })
        }
    }


    @Preview(showBackground = true)
    @Composable
    fun Community(
        communityViewModel: CommunityViewModel = viewModel(),
        @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
    ) {
        val state = MySwipeRefreshState(NORMAL)
        val scope = rememberCoroutineScope()
        var list: List<CommunityEntity> by remember {
            mutableStateOf(communityViewModel.getCommunityList())
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
                    ThreadPoolManager.getInstance().addTask("community") {
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
                communityList = communityViewModel.getCommunityList(),
                modifier = _modifier
            )
        }
    }
}
