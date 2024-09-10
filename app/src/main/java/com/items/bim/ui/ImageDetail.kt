package com.items.bim.ui

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.items.bim.R
import com.items.bim.common.consts.StyleCommon
import com.items.bim.common.consts.SystemApp
import com.items.bim.common.ui.FullScreenImage
import com.items.bim.common.util.ShareUtil
import com.items.bim.common.util.Utils
import com.items.bim.viewmodel.ImageViewModel
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.launch
import java.io.File


private val logger = KotlinLogging.logger {}

@SuppressLint("CoroutineCreationDuringComposition", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ImageDetail(
    imageViewModel: ImageViewModel,
    mainController: NavHostController
) {
    val images = imageViewModel.getImageList(imageViewModel.groupPath)
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState {
        images.size
    }
    var visible by remember {
        mutableStateOf(true)
    }
    // 定位
    coroutineScope.launch {
        pagerState.scrollToPage(imageViewModel.imageDetail.value.index)
    }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = SystemApp.snackBarHostState, modifier = Modifier.padding(0.dp))
        },
        topBar = {
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                ImageTopBar(imageViewModel.imageDetail.value.name, mainController)
            }
        },
        bottomBar = {
            AnimatedVisibility(visible = visible,
                enter = fadeIn(),
                exit = fadeOut()) {
                GetBottomBar(imageViewModel.imageDetail.value.filePath, onChange = {
                    // 这里是异步
                    imageViewModel.reloadPath(imageViewModel.groupPath)
                })
            }
        },
        modifier= Modifier.fillMaxSize()
    ) {  _ ->
        // 详情页面
        HorizontalPager(
            state = pagerState,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .navigationBarsPadding()
//                .padding(innerPadding)
        ) { it ->
            Column(
                modifier= Modifier.fillMaxSize(),
                verticalArrangement=Arrangement.SpaceEvenly,
                horizontalAlignment=Alignment.CenterHorizontally,) {
                Button(
                    onClick = {
                        visible = !visible
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = StyleCommon.ZERO_SHAPE,
                    colors = ButtonDefaults.buttonColors(Color.White),
                ) {
                    FullScreenImage(
                        fileEntity = images[it],
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
fun GetBottomBar(filePath: String, onChange: () -> Unit) {
    val scope = rememberCoroutineScope()
    // 分享
//    val sheetState = rememberModalBottomSheetState();
//    var visible by remember {
//        mutableStateOf(false)
//    }
    var selectedIndex by remember { mutableIntStateOf(-1) }
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
            val iconModifier = Modifier.size(30.dp)
            val message = stringResource(id = R.string.empty_ui)
            val activity = LocalContext.current as Activity
            IconButton(
                onClick = { Utils.message(scope, message, SystemApp.snackBarHostState) },
                modifier = buttonModifier
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        modifier = iconModifier,
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Localized description"
                    )
                    Text(
                        text = "编辑",
                        fontSize = 12.sp,
                    )
                }
            }
            IconButton(
                onClick = {
//                    visible = !visible
                    val file = File(filePath)
                    ShareUtil.shareImage(
                        activity,
                        "com.items.bim.fileprovider",
                        file.name,
                        file
                    )
                },
                modifier = buttonModifier
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        modifier = iconModifier,
                        imageVector = Icons.Filled.Share,
                        contentDescription = "Localized description"
                    )
                    Text(text = "分享", fontSize = 12.sp)
                }
            }
            IconButton(
                onClick = {
                    scope.launch {
                        if (File(filePath).delete()) {
                            SystemApp.snackBarHostState.showSnackbar(
                                "删除成功",
                                actionLabel = "关闭",
                                // Defaults to SnackbarDuration.Short
                                duration = SnackbarDuration.Short
                            )
                            logger.info { "文件删除成功" }
                            onChange()
                        } else {
                            SystemApp.snackBarHostState.showSnackbar(
                                "删除失败",
                                actionLabel = "关闭",
                                // Defaults to SnackbarDuration.Short
                                duration = SnackbarDuration.Short
                            )
                        }
                    }

                },
                modifier = buttonModifier
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        modifier = iconModifier,
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Localized description"
                    )
                    Text(text = "删除", fontSize = 12.sp)
                }
            }
            IconButton(
                onClick = {  selectedIndex = 0 },
                modifier = buttonModifier
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        modifier = iconModifier,
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = "Localized description"
                    )
                    Text(text = "更多", fontSize = 12.sp)
                }
            }
            DropdownMenu(
                expanded = selectedIndex == 0,
                onDismissRequest = { selectedIndex = -1 }) {
                DropdownMenuItem(text = {
                    Text(text = "图片转化gif")
                }, onClick = {
                })
                DropdownMenuItem(text = {
                    Text(text = "文字识别")
                }, onClick = {
                    logger.info { "开始导入图片" }
                    selectedIndex = 1
                })
                DropdownMenuItem(text = {
                    Text(text = "查看文件hash值")
                }, onClick = {
                    selectedIndex = 2
                })
            }
        }
    }
}