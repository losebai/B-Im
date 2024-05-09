package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.common.consts.PRODUCT_DEVICE_NUMBER
import com.example.myapplication.common.consts.UserId
import com.example.myapplication.common.ui.ImageGroupButton
import com.example.myapplication.common.ui.ImageListView
import com.example.myapplication.common.ui.pullRefresh
import com.example.myapplication.common.util.ThreadPoolManager
import com.example.myapplication.common.util.Utils
import com.example.myapplication.config.MenuRouteConfig
import com.example.myapplication.config.PageRouteConfig
import com.example.myapplication.entity.ImageEntity
import com.example.myapplication.entity.UserEntity
import com.example.myapplication.remote.entity.AppUserEntity
import com.example.myapplication.service.UserService
import com.example.myapplication.ui.AppTheme
import com.example.myapplication.ui.PhotoDataSet
import com.example.myapplication.ui.SearchUser
import com.example.myapplication.ui.SettingHome
import com.example.myapplication.ui.UserList
import com.example.myapplication.viewmodel.ImageViewModel
import com.example.myapplication.viewmodel.UserViewModel
import kotlinx.coroutines.launch
import mu.KotlinLogging


private val logger = KotlinLogging.logger {}


class MainActivity : AppCompatActivity() {


    private var appBase: AppBase = AppBase()

    private val userService: UserService = UserService()

    private lateinit var userViewModel: UserViewModel;

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
            appUserEntity.deviceNumber = PRODUCT_DEVICE_NUMBER
            val user = userService.gerUserByNumber(PRODUCT_DEVICE_NUMBER)
            if (user.id != null) {
                UserId = user.id
                appUserEntity.id = user.id
                userViewModel.userEntity.id = user.id
                userViewModel.userEntity.name = user.name
                userViewModel.userEntity.imageUrl = user.imageUrl
                userViewModel.userEntity.note = user.note
            } else {
                userService.save(appUserEntity)
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme() {
                userViewModel = viewModel<UserViewModel>()
                appBase.imageViewModel = viewModel<ImageViewModel>()
//              ImageUtils.check(LocalContext.current, this)
                appBase.navHostController = rememberNavController()
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
                        PageHost(appBase.imageViewModel)
                    }
                    // 二级页面 相片页
                    composable(PageRouteConfig.IMAGE_PAGE_ROUTE) {
                        PhotoDataSet(appBase.imageViewModel, appBase.navHostController)
                    }
                }
            }
        }
    }

    @SuppressLint("CoroutineCreationDuringComposition")
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @Preview(showBackground = true)
    @Composable
    fun PageHost(imageViewModel: ImageViewModel = viewModel()) {
        val scope = rememberCoroutineScope()
        var userImages: List<UserEntity> = remember {
            mutableListOf()
        }
        val searchUserEntity by remember {
            mutableStateOf(AppUserEntity())
        }
        ModalNavigationDrawer(drawerState = appBase.settingDrawerState, drawerContent = {
            ModalDrawerSheet {
                ThreadPoolManager.getInstance().addTask("init") {
                    if (userViewModel.userEntity.id != null) {
                        val user = userService.getUser(userViewModel.userEntity.id)
                        userViewModel.userEntity = user
                    }
                }
                SettingHome(userViewModel.userEntity)
            }
        }) {
            appBase.Context(content = { innerPadding ->
                val mod = Modifier
                    .padding(innerPadding)

                when (appBase.Page) {
                    MenuRouteConfig.ROUTE_IMAGE -> {
                        if (!appBase.isLoadImage) {
                            logger.info { "未加载" }
                            Column(
                                modifier = mod
                                    .fillMaxHeight()
                                    .padding(20.dp),
                                verticalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Text(
                                    text = stringResource(R.string.image_empty),
                                    color = Color.Yellow,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp
                                )
                            }
                        } else {
                            logger.info { "加载" }
                            ScaffoldExample(imageViewModel, mod)
                        }
                    }

                    MenuRouteConfig.ROUTE_COMMUNITY -> Community(mod)
                    MenuRouteConfig.ROUTE_SETTING -> {
                        Text(text = "设置", modifier = mod)
//                        ImageUtils.CheckPermission()
                    }

                    MenuRouteConfig.ROUTE_MESSAGE -> {
                        scope.launch {
                            appBase.snackbarHostState.showSnackbar(
                                getString(R.string.empty_ui),
                                actionLabel = "关闭",
                                // Defaults to SnackbarDuration.Short
                                duration = SnackbarDuration.Short
                            )
                        }
                    }

                    MenuRouteConfig.ROUTE_USERS -> {
                        Column(modifier = mod) {
                            SearchUser(searchUserEntity.name, onValueChange = {
                                searchUserEntity.name = it
                            })
                            UserList(userImages, onClick = {
                            })
                            ThreadPoolManager.getInstance().addTask("Actity") {
                                logger.info { "开始加载联系人" }
                                userImages = userService.getList(searchUserEntity)
                            }
                        }
                    }
                }
            }, topBar = {
                logger.info { "GetTopAppBar load ...." }
                appBase.GetTopAppBar(userViewModel.userEntity)
            }, floatingActionButton = {
            })
        }
    }


    @Composable
    fun ScaffoldExample(
        imageViewModel: ImageViewModel,
        modifier: Modifier = Modifier
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 96.dp),
            modifier = modifier
                .padding(15.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(imageViewModel.groupList.size) { photo ->
                ImageGroupButton(imageViewModel.groupList[photo]) { item ->
                    if (item.isDir) {
                        imageViewModel.groupName = item.name
                        imageViewModel.groupPath = item.file?.parent.toString()
                    }
                    appBase.navHostController.navigate(PageRouteConfig.IMAGE_PAGE_ROUTE)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun Community(modifier: Modifier = Modifier) {
//    appBase.Context { innerPadding ->
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(10.dp)
        // 设置点击波纹效果，注意如果 CardDemo() 函数不在 MaterialTheme 下调用
        // 将无法显示波纹效果
    ) {
        Column(
            modifier = Modifier.padding(15.dp) // 内边距
        ) {
            Text(
                buildAnnotatedString {
                    append("欢迎来到 ")
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.W900,
                            color = Color(0xFF4552B8)
                        )
                    ) {
                        append("Jetpack Compose 博物馆")
                    }
                }
            )
            val list = ArrayList<ImageEntity>()

            list.add(
                ImageEntity(
                    null,
                    "name",
                    R.drawable.test.toString()
                )
            );
            list.add(
                ImageEntity(
                    null,
                    "name",
                    R.drawable.test.toString()
                )
            );
            ImageListView(list) {}
        }
//        }
    }
}





