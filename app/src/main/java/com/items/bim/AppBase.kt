package com.items.bim

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.items.bim.common.consts.SystemApp
import com.items.bim.common.consts.UserStatus
import com.items.bim.common.ui.AppBarButton
import com.items.bim.common.ui.HeadImage
import com.items.bim.common.util.ThreadPoolManager
import com.items.bim.config.MenuRouteConfig
import com.items.bim.config.PageRouteConfig
import com.items.bim.entity.AppUserEntity
import com.items.bim.viewmodel.HomeViewModel
import com.items.bim.viewmodel.UserViewModel
import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

@SuppressLint("CoroutineCreationDuringComposition", "ResourceAsColor")
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AppGetTopAppBar(userViewModel: UserViewModel,
                    nvHostController: NavHostController,
                    homeViewModel: HomeViewModel) {
    val isPositive by remember {
        derivedStateOf {
            when(homeViewModel.page){
                MenuRouteConfig.TOOLS_ROUTE -> {
                    false
                }
                MenuRouteConfig.ROUTE_COMMUNITY -> {
                    false
                }
                MenuRouteConfig.ROUTE_MESSAGE -> {
                    true
                }
                MenuRouteConfig.ROUTE_USERS -> {
                    true
                }
                else -> {
                    false
                }
            }
        }
    }
    Column(modifier = Modifier) {
        if (isPositive) {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    var expanded by remember { mutableStateOf(false) }
                    IconButton(onClick = { expanded = true }) {
                        Icon(
                            modifier = Modifier.size(50.dp),
                            imageVector = Icons.Outlined.Add,
                            contentDescription = "Localized description"
                        )
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }) {
                        DropdownMenuItem(text = {
                            Text(text = "图库中心")
                        }, onClick = {
                            nvHostController.navigate(PageRouteConfig.IMAGE_GROUP_LIST)
                            logger.debug { "图库中心" }
                        })
                    }
                },
                title = {
                    Row {
                        HeadImage(
                            userViewModel.userEntity,
                            modifier = Modifier.size(30.dp),
                        ) {
                            homeViewModel.settingDrawerState = DrawerState(DrawerValue.Open)
                        }
                        Column(modifier = Modifier.padding(start = 10.dp)) {
                            Text(text = userViewModel.userEntity.name, fontSize = 12.sp)
                            val colorStatus = when (SystemApp.userStatus) {
                                UserStatus.INIT -> colorResource(R.color.INIT)
                                UserStatus.OFF_LINE -> colorResource(R.color.OFF_LINE)
                                UserStatus.ON_LINE -> colorResource(R.color.ON_LINE)
                                UserStatus.HiDING -> colorResource(R.color.HiDING)
                            }
                            Text(text = SystemApp.userStatus.tag,
                                modifier = Modifier
                                    .drawWithContent {
                                        drawIntoCanvas {
                                            val paint = Paint().apply {
                                                color = colorStatus
                                            }
                                            it.drawCircle(
                                                center = Offset(
                                                    x = 15f,
                                                    y = size.height / 2
                                                ),
                                                radius = 10f,
                                                paint = paint
                                            )
                                        }
                                        drawContent()
                                    }
                                    .padding(start = 15.dp),
                                fontSize = 10.sp,
                                color = colorStatus)
                        }
                    }
                },
                modifier = Modifier.height(70.dp)
            )
            Divider(color = Color.Black)
        }
    }

}

@Composable
fun AppGetBottomBar(homeViewModel: HomeViewModel,
                 userViewModel: UserViewModel) {
    val IconModifier = Modifier
    BottomAppBar(
        modifier = Modifier.height(50.dp),
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary,
        contentPadding = PaddingValues(5.dp, 5.dp)
    ) {
        Row(
            Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val activeColor = colorResource(R.color.active_button)
            AppBarButton(homeViewModel.page == MenuRouteConfig.ROUTE_MESSAGE,
                Icons.Outlined.MailOutline,
                activeColor, "消息", IconModifier, onClick = {
                    homeViewModel.page = MenuRouteConfig.ROUTE_MESSAGE
                })
            AppBarButton(homeViewModel.page == MenuRouteConfig.ROUTE_USERS, Icons.Outlined.AccountCircle, activeColor, "联系人", IconModifier, onClick = {
                ThreadPoolManager.getInstance().addTask("user", "UserList"){
                    userViewModel.referUser()
                }
                homeViewModel.page = MenuRouteConfig.ROUTE_USERS
            })
            AppBarButton(homeViewModel.page == MenuRouteConfig.TOOLS_ROUTE,  Icons.Outlined.Build,activeColor, "游戏", IconModifier, onClick = {
                homeViewModel.page = MenuRouteConfig.TOOLS_ROUTE
            })
            AppBarButton(homeViewModel.page == MenuRouteConfig.ROUTE_COMMUNITY,  Icons.Outlined.FavoriteBorder, activeColor, "社区", IconModifier, onClick = {
                homeViewModel.page = MenuRouteConfig.ROUTE_COMMUNITY
            })
        }
    }
}


@Composable
fun Context(
    content: @Composable (PaddingValues) -> Unit,
    topBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = { },
    bottomBar: @Composable () -> Unit = { },
) {
    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = SystemApp.snackBarHostState,
            ) {
                Snackbar(it, containerColor = Color.Black)
            }
        },
        topBar = topBar,
        bottomBar = bottomBar,
        floatingActionButton = { floatingActionButton() },
        modifier = Modifier
            .padding(0.dp)
//            .background(Color.White)
        ,
        content = content
    )
}