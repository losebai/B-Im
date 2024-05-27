package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.common.consts.SystemApp
import com.example.myapplication.common.ui.LOADING_MORE
import com.example.myapplication.common.ui.LoadingIndicator
import com.example.myapplication.common.ui.MySwipeRefresh
import com.example.myapplication.common.ui.MySwipeRefreshState
import com.example.myapplication.common.ui.NORMAL
import com.example.myapplication.common.ui.REFRESHING
import com.example.myapplication.common.util.MediaStoreUtils
import com.example.myapplication.common.util.ThreadPoolManager
import com.example.myapplication.common.util.Utils
import com.example.myapplication.common.util.toFileEntity
import com.example.myapplication.config.MenuRouteConfig
import com.example.myapplication.config.PageRouteConfig
import com.example.myapplication.entity.CommunityEntity
import com.example.myapplication.entity.FileEntity
import com.example.myapplication.remote.entity.AppUserEntity
import com.example.myapplication.remote.entity.toUserEntity
import com.example.myapplication.ui.AppTheme
import com.example.myapplication.ui.CommunityHome
import com.example.myapplication.ui.ImageGroupList
import com.example.myapplication.ui.ImageSelect
import com.example.myapplication.ui.MessagesDetail
import com.example.myapplication.ui.MessagesList
import com.example.myapplication.ui.PhotoDataSet
import com.example.myapplication.ui.SearchUser
import com.example.myapplication.ui.SettingHome
import com.example.myapplication.ui.UserList
import com.example.myapplication.viewmodel.CommunityViewModel
import com.example.myapplication.viewmodel.ImageViewModel
import com.example.myapplication.viewmodel.MessagesViewModel
import com.example.myapplication.viewmodel.UserViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import mu.KotlinLogging


private val logger = KotlinLogging.logger {}


class MainActivity : AppCompatActivity() {


    private var appBase: AppBase = AppBase()

    private lateinit var userViewModel: UserViewModel;

    private val communityViewModel: CommunityViewModel by viewModels()

    private lateinit var messagesViewModel: MessagesViewModel;

    private val searchUserEntity by mutableStateOf(AppUserEntity())

    override fun finish() {
        super.finish()
    }

    override fun onResume() {
        super.onResume()
    }

    private fun init() {
        // 初始化的时候保存和更新
        // 默认账户信息
        ThreadPoolManager.getInstance().addTask("init") {
            val appUserEntity = Utils.randomUser()
            appUserEntity.deviceNumber = SystemApp.PRODUCT_DEVICE_NUMBER
            val user = userViewModel.gerUserByNumber(SystemApp.PRODUCT_DEVICE_NUMBER)
            if (user.id != 0L) {
                SystemApp.UserId = user.id
                appUserEntity.id = user.id
                userViewModel.userEntity.id = user.id
                userViewModel.userEntity.name = user.name
                userViewModel.userEntity.imageUrl = user.imageUrl
                userViewModel.userEntity.note = user.note
            } else {
                userViewModel.saveUser(appUserEntity)
            }

            logger.info { "开始加载联系人" }
            userViewModel.users = userViewModel.getReferUser(searchUserEntity)
            communityViewModel.nextCommunityPage()
            appBase.imageViewModel.getDay7Images(this)

        }
    }


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme(appBase.darkTheme) {
                appBase.imageViewModel = viewModel<ImageViewModel>()
                appBase.navHostController = rememberNavController()
                userViewModel = ViewModelProvider(
                    this,
                    UserViewModel.MessageViewModelFactory(this)
                )[UserViewModel::class.java]

                messagesViewModel =
                    ViewModelProvider(
                        this,
                        MessagesViewModel.MessageViewModelFactory(this)
                    )[MessagesViewModel::class.java]
                this.init()
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
                            appBase.navHostController
                        )
                    }
                    // 二级页面 相片页
                    composable(PageRouteConfig.IMAGE_PAGE_ROUTE) {
                        PhotoDataSet(appBase.imageViewModel, appBase.navHostController)
                    }
                    composable(PageRouteConfig.MESSAGE_ROUTE) {
                        MessagesDetail(
                            userViewModel.sendUserEntity,
                            messagesViewModel,
                            messagesViewModel.messagesDetail,
                            appBase.navHostController,
                        )
                    }
                    composable(PageRouteConfig.IMAGE_SELECTOR){
                        ImageSelect(appBase.imageViewModel){
                            appBase.navHostController.navigateUp()
                        }
                    }
                }
            }
        }
    }

    @SuppressLint("CoroutineCreationDuringComposition")
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
//    @Preview(showBackground = true)
    @Composable
    fun PageHost(
        imageViewModel: ImageViewModel,
        messagesViewModel: MessagesViewModel,
        mainController: NavHostController
    ) {
        val scope = rememberCoroutineScope()
        ModalNavigationDrawer(drawerState = appBase.settingDrawerState, drawerContent = {
            ModalDrawerSheet {
                ThreadPoolManager.getInstance().addTask("init") {
                    val user = userViewModel.getUserById(userViewModel.userEntity.id)
                    userViewModel.userEntity = user.toUserEntity()
                }
                SettingHome(userViewModel.userEntity)
            }
        }) {
            appBase.Context(content = { innerPadding ->
                val mod = Modifier
                    .padding(innerPadding)
                when (appBase.page) {
                    MenuRouteConfig.ROUTE_IMAGE -> {
                        appBase.topVisible = true
                        if (imageViewModel.dirList.isEmpty()) {
                            scope.launch {
                                SystemApp.snackBarHostState.showSnackbar(
                                    getString(R.string.image_empty),
                                    actionLabel = "关闭",
                                    duration = SnackbarDuration.Short
                                )
                            }
                        }
                        ImageGroupList(
                            imageViewModel,
                            mod.padding(10.dp),
                            appBase.navHostController
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
                            messagesViewModel.userMessagesList,
                            modifier = mod.fillMaxWidth()
                        )
                    }

                    MenuRouteConfig.ROUTE_USERS -> {
                        appBase.topVisible = true
                        Column(modifier = mod) {
                            SearchUser(
                                searchUserEntity.name, onValueChange = {
                                    searchUserEntity.name = it
                                }, modifier = Modifier
                                    .height(50.dp)
                                    .fillMaxWidth()
                                    .padding(2.dp)
                            )
                            UserList(userViewModel, onClick = {
                                userViewModel.sendUserEntity = it
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
                    ThreadPoolManager.getInstance().addTask("community") {
                        communityViewModel.clearCommunityList()
                        list = communityViewModel.nextCommunityPage()
                    }
                    logger.info { "社区下拉刷新" }
                    state.loadState = NORMAL
                }
            },
            onLoadMore = {
                scope.launch {
                    state.loadState = LOADING_MORE
                    ThreadPoolManager.getInstance().addTask("community") {
                        list = communityViewModel.nextCommunityPage()
                    }
                    logger.info { "社区上拉刷新" }
                    state.loadState = NORMAL
                }
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

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun Test() {
    val scope = rememberCoroutineScope()
    val state by mutableStateOf(MySwipeRefreshState(NORMAL))

    var list by remember {
        mutableStateOf(List(40) { "I'm item $it" })
    }

    MySwipeRefresh(
        state = state,
        indicator = { modifier, s, indicatorHeight ->
            LoadingIndicator(modifier, s, indicatorHeight)
        },
        onRefresh = {
            scope.launch {
                state.loadState = REFRESHING
                //模拟网络请求
                delay(200)
                list = List(20) { "I'm item $it" }
                state.loadState = NORMAL
            }
        },
        onLoadMore = {
            scope.launch {
                state.loadState = LOADING_MORE
                //模拟网络请求
                delay(200)
                list = list + List(20) { "I'm item ${it + list.size}" }
                state.loadState = NORMAL
            }
        }
    ) { modifier ->
//注意这里要把modifier设置过来，要不然LazyColumn不会跟随它上下拖动
        LazyColumn(modifier) {
            items(items = list, key = { it }) {
                Text(
                    text = it,
                    Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )
            }
        }
    }
}