package com.example.myapplication

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.myapplication.common.consts.StyleCommon.ZERO_PADDING
import com.example.myapplication.common.ui.DialogImageAdd
import com.example.myapplication.common.util.ImageUtils
import com.example.myapplication.common.util.ThreadPoolManager
import com.example.myapplication.common.util.Utils
import com.example.myapplication.config.MenuRouteConfig
import com.example.myapplication.entity.UserEntity
import com.example.myapplication.viewmodel.ImageViewModel
import kotlinx.coroutines.launch
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

class AppBase {

    lateinit var imageViewModel: ImageViewModel;

    lateinit var navHostController: NavHostController;

    var page by mutableStateOf(MenuRouteConfig.ROUTE_IMAGE)

    var settingDrawerState by mutableStateOf(DrawerState(DrawerValue.Closed))

    var snackbarHostState = SnackbarHostState()

    var isLoadImage by mutableStateOf(false)

    var topVisible  by mutableStateOf(false)

    var darkTheme by mutableStateOf(false)


    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable
    @Preview(showBackground = true)
    @OptIn(ExperimentalMaterial3Api::class)
    fun GetTopAppBar(appUserEntity: UserEntity = UserEntity()) {
        var expanded by remember { mutableStateOf(false) }
        var selectedIndex by remember { mutableIntStateOf(-1) }
        val coroutineScope = rememberCoroutineScope()
        if (topVisible){
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Row {
                        Row {
                            Button(
                                onClick = {
                                    settingDrawerState = DrawerState(DrawerValue.Open)
                                          },
                                modifier = Modifier.size(40.dp),
                                shape = RoundedCornerShape(50),
                                contentPadding = ZERO_PADDING,
                                colors = ButtonDefaults.buttonColors(Color.White)
                            ) {
                                Surface(
                                    shape = CircleShape,
                                    border = BorderStroke(0.dp, Color.Gray)
                                ) {
                                    Image(
                                        painter = rememberAsyncImagePainter(
                                            model = ImageRequest.Builder(LocalContext.current)
                                                .data(Utils.stringOrNull(appUserEntity.imageUrl))
                                                .size(100)
                                                .build()
                                        ),
                                        contentDescription = null,
                                        modifier = Modifier.size(40.dp),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                            }
                            TextButton(
                                onClick = { /*TODO*/ },
                                contentPadding = ZERO_PADDING,
                            ) {
                                Text(text =appUserEntity.name)
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
                                        ThreadPoolManager.getInstance().addTask("imageLoad") {
                                            logger.info { "开始导入图片" }
                                            imageViewModel.groupList.clear();
                                            imageViewModel.groupList.addAll(
                                                ImageUtils.getDirectoryList(
                                                    ImageUtils.cameraDirPath
                                                )
                                            );
                                            imageViewModel.groupList.addAll(
                                                ImageUtils.getDirectoryList(
                                                    ImageUtils.galleryDirPath
                                                )
                                            );
                                            isLoadImage = true
                                            coroutineScope.launch {
                                                snackbarHostState.showSnackbar("图片导入完成 共${imageViewModel.groupList.size}")
                                            }
                                        }
                                        expanded = false
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
    }

    @Preview(showBackground = true)
    @Composable
    fun GetBottomBar() {
        BottomAppBar(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.primary,
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val buttonModifier = Modifier.size(70.dp)
                val IconModifier = Modifier.size(30.dp)
                IconButton(
                    onClick = { page = MenuRouteConfig.ROUTE_MESSAGE },
                    modifier = buttonModifier
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            modifier = IconModifier,
                            imageVector = Icons.Filled.MailOutline,
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
                            imageVector = Icons.Filled.AccountCircle,
                            contentDescription = "Localized description"
                        )
                        Text(
                            text = "联系人",
                            fontSize = 12.sp,
                        )
                    }
                }
                IconButton(
                    onClick = { page = MenuRouteConfig.ROUTE_IMAGE },
                    modifier = buttonModifier
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            modifier = IconModifier,
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "Localized description"
                        )
                        Text(
                            text = "相册",
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
                            imageVector = Icons.Filled.Home,
                            contentDescription = "Localized description"
                        )
                        Text(text = "社区", fontSize = 12.sp)
                    }
                }
            }
        }
        Divider()
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
                    hostState = snackbarHostState,
                    modifier = Modifier.padding(0.dp)
                )
            },
            topBar = topBar,
            bottomBar = bottomBar,
            floatingActionButton = { floatingActionButton() },
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.background),
            content = content
        )
    }
}
