package com.items.bim.ui

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.items.bim.common.consts.StyleCommon
import com.items.bim.common.consts.SystemApp
import com.items.bim.common.consts.SystemApp.snackBarHostState
import com.items.bim.common.ui.DialogImageAdd
import com.items.bim.common.ui.FullScreenImage
import com.items.bim.common.ui.ImageGroupButton
import com.items.bim.common.util.ImageUtils
import com.items.bim.common.util.ThreadPoolManager
import com.items.bim.config.MenuRouteConfig
import com.items.bim.config.PageRouteConfig
import com.items.bim.dto.FileEntity
import com.items.bim.viewmodel.ImageViewModel
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.launch


val ImageModifier: Modifier = Modifier
    .size(100.dp)
    .padding(1.dp);

private val logger = KotlinLogging.logger {}

private val TEXT_ROW_MODIFIER = Modifier.fillMaxWidth(0.8f)



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageTopBar(name: String, mainController: NavHostController) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text(
                name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        // 返回
        navigationIcon = {
            IconButton(onClick = {
                mainController.navigateUp()
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Localized description"
                )
            }
        },
        actions = {
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Localized description"
                )
            }
        },
        scrollBehavior = scrollBehavior,
    )
}

/**
 * 图片列表
 */
@Composable
fun PhotoDataSetBody(
    list: List<FileEntity>,
    modifier: Modifier = Modifier,
    onClick: (FileEntity) -> Unit
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = modifier,
        reverseLayout = false
    ) {
        items(list.size) { photo ->
            AsyncImage(
                ImageRequest.Builder(LocalContext.current)
                    .data(list[photo].location)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = ImageModifier
                    .clip(RoundedCornerShape(1))
                    .clickable {
                        onClick(list[photo])
                    },
            )
        }
    }
}

/**
 * 图片一级详情页面
 * @param [imageViewModel]
 * @param [mainController]
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun PhotoDataSet(
    imageViewModel: ImageViewModel,
    mainController: NavHostController = rememberNavController()
) {
    val path = imageViewModel.groupPath
    if (path != "") {
        ThreadPoolManager.getInstance().addTask("image", "loadPath"){
            imageViewModel.loadPath(path)
        }
    }
    val images = imageViewModel.getImageList(path)
    logger.info { "PhotoDataSet重组了 $path" }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState, modifier = Modifier.padding(0.dp))
        },
        topBar = {
            ImageTopBar(imageViewModel.groupName, mainController)
        },
    ) { innerPadding ->
        PhotoDataSetBody(
            images,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            imageViewModel.imageDetail.value = it
            mainController.navigate(PageRouteConfig.IMAGE_DETAIL)
        }
    }
}

/**
 * 图片分组列表
 * @param [imageViewModel]
 * @param [modifier]
 * @param [navHostController]
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageGroupList(
    imageViewModel: ImageViewModel,
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
) {
    logger.info { "ImageGroupList ...." }
    Column {
        TopAppBar(title = { }, navigationIcon = {
            IconButton(onClick = {
                navHostController.navigateUp()
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "返回"
                )
            }
        }, actions = {
            var selectedIndex by remember { mutableIntStateOf(-1) }
            IconButton(onClick = { selectedIndex = 0 }) {
                Icon(
                    modifier = Modifier.size(50.dp),
                    imageVector = Icons.Outlined.List,
                    contentDescription = "Localized description"
                )
            }
            DropdownMenu(
                expanded = selectedIndex == 0,
                onDismissRequest = { selectedIndex = -1 }) {
                DropdownMenuItem(text = {
                    Text(text = "清空")
                }, onClick = {
                    imageViewModel.reload()
                })
                DropdownMenuItem(text = {
                    Text(text = "导入")
                }, onClick = {
                    logger.info { "开始导入图片" }
                    imageViewModel.reload()
                    selectedIndex = 1
                })
                DropdownMenuItem(text = {
                    Text(text = "新建文件夹")
                }, onClick = {
                    selectedIndex = 2
                })
            }
            when (selectedIndex) {
                1 -> {
                    ImportImages(imageViewModel = imageViewModel) {
                        selectedIndex = -1
                    }
                }
                2 -> {
                    DialogImageAdd(onDismissRequest = {
                        selectedIndex = -1
                    })
                }
            }
        })
        Row(horizontalArrangement = Arrangement.SpaceAround) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 96.dp),
                modifier = modifier
                    .padding(15.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(imageViewModel.dirList.size) { photo ->
                    ImageGroupButton(imageViewModel.dirList[photo]) { item ->
                        if (item.isDir) {
                            imageViewModel.groupName = item.name
                            imageViewModel.groupPath = item.parentPath
                        }
                        navHostController.navigate(PageRouteConfig.IMAGE_PAGE_ROUTE)
                    }
                }
            }
        }
    }
}


@Composable
fun ImportImages(
    imageViewModel: ImageViewModel = viewModel(),
    onDismissRequest: () -> Unit
) {
    val checkeds = remember { mutableStateListOf(false, false, false, false) }
    var currentProgress by remember { mutableFloatStateOf(0f) }
    var loading by remember { mutableStateOf(false) }
    var enabled by remember {
        mutableStateOf(true)
    }
    Dialog(onDismissRequest = onDismissRequest) {
        Box(modifier = Modifier.background(Color.White)) {
            Column(
                modifier = Modifier
                    .padding(20.dp),
            ) {
                Row {
                    Text(text = "本机相册", modifier = TEXT_ROW_MODIFIER)
                    Switch(
                        checked = checkeds[0],
                        onCheckedChange = {
                            checkeds[0] = it
                        },
                        colors = SwitchDefaults.colors(Color.Black, Color.White)
                    )
                }
                Row {
                    Text(text = "相机", modifier = TEXT_ROW_MODIFIER)
                    Switch(
                        checked = checkeds[1],
                        onCheckedChange = {
                            checkeds[1] = it
                        },
                        colors = SwitchDefaults.colors(Color.Black, Color.White)
                    )
                }
                Row {
                    Text(text = "腾讯QQ", modifier = TEXT_ROW_MODIFIER)
                    Switch(
                        checked = checkeds[2],
                        onCheckedChange = {
                            checkeds[2] = it
                        },
                        colors = SwitchDefaults.colors(Color.Black, Color.White)
                    )
                }
                Row {
                    Text(text = "微信", modifier = TEXT_ROW_MODIFIER)
                    Switch(
                        checked = checkeds[3],
                        onCheckedChange = {
                            checkeds[3] = it
                        },
                        colors = SwitchDefaults.colors(Color.Black, Color.White)
                    )
                }
                if (loading) {
                    LinearProgressIndicator(
                        progress = currentProgress,
                        modifier = Modifier.fillMaxWidth()
                    )
                } else {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedButton(onClick = {
                            enabled = false
                            loading = true
                            ThreadPoolManager.getInstance().addTask("imageLoad") {
                                for ((i, path) in SystemApp.IMAGE_PATHS.withIndex()) {
                                    currentProgress =
                                        ((i + 1) / SystemApp.IMAGE_PATHS.size).toFloat()
                                    if (checkeds[i]) {
                                        imageViewModel.dirList.addAll(
                                            ImageUtils.getDirectoryList(path)
                                        );
                                    }
                                }
                            }
                            enabled = true
                            onDismissRequest()
                        }, enabled = enabled) {
                            Text(text = "确定")
                        }
                    }
                }
            }
        }
    }
}
