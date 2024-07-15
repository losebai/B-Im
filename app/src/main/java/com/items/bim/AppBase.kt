package com.items.bim

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.outlined.Home
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.items.bim.common.consts.SystemApp
import com.items.bim.common.consts.UserStatus
import com.items.bim.common.ui.AppBarButton
import com.items.bim.common.ui.DialogImageAdd
import com.items.bim.common.ui.HeadImage
import com.items.bim.common.ui.buttonClick
import com.items.bim.config.MenuRouteConfig
import com.items.bim.config.PageRouteConfig
import com.items.bim.entity.UserEntity
import com.items.bim.ui.ImportImages
import com.items.bim.viewmodel.ImageViewModel
import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

class AppBase {

    var imageViewModel: ImageViewModel = ImageViewModel()

    var page by mutableStateOf(MenuRouteConfig.ROUTE_USERS)

    var settingDrawerState by mutableStateOf(DrawerState(DrawerValue.Closed))

    var topVisible by mutableStateOf(false)

    var darkTheme by mutableStateOf(false)


    @SuppressLint("CoroutineCreationDuringComposition", "ResourceAsColor")
    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    fun GetTopAppBar(appUserEntity: UserEntity, nvHostController: NavHostController) {
        var expanded by remember { mutableStateOf(false) }

        Column(modifier = Modifier) {
            if (topVisible) {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    actions = {
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
                                logger.info { "图库中心" }
                            })
                        }
                    },
                    title = {
                        Row {
                            HeadImage(
                                appUserEntity,
                                modifier = Modifier.size(30.dp),
                            ) {
                                settingDrawerState = DrawerState(DrawerValue.Open)
                            }
                            Column(modifier = Modifier.padding(start = 10.dp)) {
                                Text(text = appUserEntity.name, fontSize = 12.sp)
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
    fun GetBottomBar() {
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
                AppBarButton(page == MenuRouteConfig.ROUTE_MESSAGE,
                    Icons.Outlined.MailOutline,
                    activeColor, "消息", IconModifier, onClick = {
                    page = MenuRouteConfig.ROUTE_MESSAGE
                })
                AppBarButton(page == MenuRouteConfig.ROUTE_USERS, Icons.Outlined.AccountCircle, activeColor, "联系人", IconModifier, onClick = {
                    page = MenuRouteConfig.ROUTE_USERS
                })
                AppBarButton(page == MenuRouteConfig.TOOLS_ROUTE,  Icons.Outlined.Build,activeColor, "游戏", IconModifier, onClick = {
                    page = MenuRouteConfig.TOOLS_ROUTE
                })
                AppBarButton(page == MenuRouteConfig.ROUTE_COMMUNITY,  Icons.Outlined.FavoriteBorder, activeColor, "社区", IconModifier, onClick = {
                    page = MenuRouteConfig.ROUTE_COMMUNITY
                })
            }
        }
//        Divider(color = Color.Black)
    }


    @Composable
    fun Context(
        content: @Composable (PaddingValues) -> Unit,
        topBar: @Composable () -> Unit = {
        },
        bottomBar: @Composable () -> Unit = { GetBottomBar() },
        floatingActionButton: @Composable () -> Unit = { },
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
                .background(Color.White),
            content = content
        )
    }
}
