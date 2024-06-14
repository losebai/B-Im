package com.example.myapplication

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.myapplication.common.consts.StyleCommon.ZERO_PADDING
import com.example.myapplication.common.consts.SystemApp
import com.example.myapplication.common.consts.UserStatus
import com.example.myapplication.common.ui.DialogImageAdd
import com.example.myapplication.common.ui.HeadImage
import com.example.myapplication.common.util.ImageUtils
import com.example.myapplication.common.util.ThreadPoolManager
import com.example.myapplication.common.util.Utils
import com.example.myapplication.config.MenuRouteConfig
import com.example.myapplication.entity.UserEntity
import com.example.myapplication.ui.ImportImages
import com.example.myapplication.viewmodel.ImageViewModel
import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

class AppBase {

    lateinit var imageViewModel: ImageViewModel;

    var page by mutableStateOf(MenuRouteConfig.TOOLS_ROUTE)

    var settingDrawerState by mutableStateOf(DrawerState(DrawerValue.Closed))

    private var isLoadImage by mutableStateOf(false)

    var topVisible  by mutableStateOf(false)

    var darkTheme by mutableStateOf(false)


    @SuppressLint("CoroutineCreationDuringComposition", "ResourceAsColor")
    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    fun GetTopAppBar(appUserEntity: UserEntity = UserEntity()) {
        var expanded by remember { mutableStateOf(false) }
        var selectedIndex by remember { mutableIntStateOf(-1) }
        var importImaged by remember { mutableStateOf(false) }
        Column {
            if (topVisible){
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Row {
                            Row {
                                HeadImage(appUserEntity,
                                    modifier = Modifier.size(45.dp),
                                ) {
                                    settingDrawerState = DrawerState(DrawerValue.Open)
                                }
                                Column(modifier = Modifier.padding(start = 10.dp)) {
                                    Text(text =appUserEntity.name, fontSize = 18.sp)
                                    val colorStatus = when(SystemApp.userStatus){
                                        UserStatus.INIT -> colorResource(R.color.INIT)
                                        UserStatus.OFF_LINE -> colorResource(R.color.OFF_LINE)
                                        UserStatus.ON_LINE -> colorResource(R.color.ON_LINE)
                                        UserStatus.HiDING -> colorResource(R.color.HiDING)
                                    }
                                    Text(text = SystemApp.userStatus.tag,
                                        modifier = Modifier.drawWithContent {
                                            drawIntoCanvas {
                                                val paint = Paint().apply {
                                                    color = colorStatus
                                                }
                                                it.drawCircle(
                                                    center = Offset(x = 15f, y = size.height / 2),
                                                    radius = 10f,
                                                    paint = paint
                                                )
                                            }
                                            drawContent()
                                        }.padding(start = 15.dp),
                                        fontSize = 10.sp,
                                        color = colorStatus)
                                }
                            }
                            Row(
                                horizontalArrangement = Arrangement.End,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column {
                                    IconButton(onClick = { expanded = true }) {
                                        Icon(
                                            modifier = Modifier
                                                .padding(0.dp)
                                                .fillMaxSize(),
                                            imageVector = Icons.Filled.Add,
                                            contentDescription = "Localized description"
                                        )
                                    }
                                    DropdownMenu(
                                        expanded = expanded,
                                        onDismissRequest = { expanded = false }) {
                                        DropdownMenuItem(text = {
                                            Text(text = "导入")
                                        }, onClick = {
                                            logger.info { "开始导入图片" }
                                            imageViewModel.reload()
                                            importImaged = true
                                            expanded = false
                                            isLoadImage = true
                                        })
                                        DropdownMenuItem(text = {
                                            Text(text = "新建文件夹")
                                        }, onClick = {
                                            selectedIndex = 1
                                            expanded = false
                                        })
                                    }
                                }
                                when (selectedIndex) {
                                    0 -> {
                                    }
                                    1 -> {
                                        DialogImageAdd(onDismissRequest = {
                                            selectedIndex = -1
                                        })
                                    }
                                }
                            }
                        }
                    }
                )
            }
            if (importImaged){
                ImportImages(imageViewModel = imageViewModel){
                    importImaged = false
                    this@AppBase.page = MenuRouteConfig.ROUTE_IMAGE
                }
            }
            Divider(color = Color.Black)
        }

    }

    @Preview(showBackground = true)
    @Composable
    fun GetBottomBar() {
        val buttonModifier = Modifier.size(70.dp)
        val IconModifier = Modifier.size(30.dp)
        BottomAppBar(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.primary,
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { page = MenuRouteConfig.ROUTE_MESSAGE },
                    modifier = buttonModifier
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            modifier = IconModifier,
                            imageVector = Icons.Outlined.MailOutline,
                            contentDescription = "Localized description"
                        )
                        Text(
                            text = "消息",
                            fontSize = 12.sp,
                        )
                    }
                }
                IconButton(
                    onClick = { page = MenuRouteConfig.ROUTE_USERS },
                    modifier = buttonModifier
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            modifier = IconModifier,
                            imageVector = Icons.Outlined.AccountCircle,
                            contentDescription = "Localized description"
                        )
                        Text(
                            text = "联系人",
                            fontSize = 12.sp,
                        )
                    }
                }
                IconButton(
                    onClick = { page = MenuRouteConfig.TOOLS_ROUTE },
                    modifier = buttonModifier
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            modifier = IconModifier,
                            imageVector = Icons.Outlined.Home,
                            contentDescription = "Localized description"
                        )
                        Text(
                            text = "游戏",
                            fontSize = 12.sp,
                        )
                    }
                }
                IconButton(
                    onClick = { page = MenuRouteConfig.ROUTE_COMMUNITY },
                    modifier = buttonModifier
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            modifier = IconModifier,
                            imageVector = Icons.Outlined.Home,
                            contentDescription = "Localized description"
                        )
                        Text(text = "社区", fontSize = 12.sp)
                    }
                }
            }
        }
        Divider(color = Color.Black)
    }


    @Composable
    fun Context(
        content: @Composable (PaddingValues) -> Unit,
        topBar: @Composable () -> Unit = {
            GetTopAppBar()
        },
        bottomBar: @Composable () -> Unit = { GetBottomBar() },
        floatingActionButton: @Composable () -> Unit = { },
    ) {
        Scaffold(
            snackbarHost = {
                SnackbarHost(
                    hostState = SystemApp.snackBarHostState,
                ){
                    Snackbar(it, containerColor= Color.Black)
                }
            },
            topBar = topBar,
            bottomBar = bottomBar,
            floatingActionButton = { floatingActionButton() },
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(Color.White),
            content = content
        )
    }
}
