package com.example.myapplication.ui

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.common.consts.StyleCommon
import com.example.myapplication.common.consts.SystemApp
import com.example.myapplication.common.consts.SystemApp.snackBarHostState
import com.example.myapplication.common.ui.FullScreenImage
import com.example.myapplication.common.ui.ImageGroupButton
import com.example.myapplication.common.util.ImageUtils
import com.example.myapplication.common.util.ThreadPoolManager
import com.example.myapplication.config.PageRouteConfig
import com.example.myapplication.entity.ImageEntity
import com.example.myapplication.viewmodel.ImageViewModel
import kotlinx.coroutines.launch
import mu.KotlinLogging


val ImageModifier: Modifier = Modifier
    .size(100.dp)
    .padding(1.dp);

private var isDetail by mutableStateOf(false)


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
                if (!isDetail) {
                    // 从列表页返回
                    mainController.navigateUp()
                } else {
                    isDetail = false
                }
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
    list: Array<ImageEntity>,
    imageViewModel: ImageViewModel,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(100.dp),
        modifier = modifier
            .fillMaxSize(),
        reverseLayout = false
    ) {
        items(list.size) { photo ->
            Button(
                onClick = {
                    imageViewModel.imageDetail = list[photo]
                    isDetail = true
                },
                shape = StyleCommon.ZERO_SHAPE,
                modifier = Modifier.clip(RoundedCornerShape(1.dp)),
                colors = ButtonDefaults.buttonColors(Color.White)
            ) {
                Image(
                    painter =
                    rememberAsyncImagePainter(list[photo].location),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = ImageModifier.clip(RoundedCornerShape(5)),
                )
            }
        }
    }
}

/**
 * 图片一级详情页面
 * @param [imageViewModel]
 * @param [mainController]
 */
//@Preview(showBackground = true)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
)
@Composable
fun PhotoDataSet(
    imageViewModel: ImageViewModel = viewModel(),
    mainController: NavHostController = rememberNavController()
) {
    val coroutineScope = rememberCoroutineScope()
    val path = imageViewModel.groupPath
    if (path != "") {
        imageViewModel.loadPath(path)
    }
    val images = imageViewModel.getImageList(path)
    val pagerState = rememberPagerState { images.size }
    var topVisible by remember {
        mutableStateOf(true)
    }
    Scaffold(
        snackbarHost = {
            if (isDetail) {
                SnackbarHost(hostState =  snackBarHostState, modifier = Modifier.padding(0.dp))
            }
        },
        topBar = {
            if (isDetail) {
                AnimatedVisibility(visible = topVisible) {
                    ImageTopBar(imageViewModel.imageDetail.name, mainController)
                }
            } else {
                ImageTopBar(imageViewModel.groupName, mainController)
            }
        },
        bottomBar = {
            if (isDetail) {
                AnimatedVisibility(visible = topVisible) {
                    GetBottomBar(imageViewModel.imageDetail.filePath) {
                        // 这里是异步
                        imageViewModel.reloadPath(path)
                    }
                }
            }
        },
    ) { innerPadding ->
        if (isDetail) {
            // 定位
            coroutineScope.launch {
                pagerState.scrollToPage(imageViewModel.imageDetail.index)
            }
            // 详情页面
            HorizontalPager(
                state = pagerState,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxHeight()
            ) { it ->
                imageViewModel.imageDetail = images[it]
                Button(
                    onClick = { topVisible = !topVisible },
                    shape = StyleCommon.ZERO_SHAPE,
                    colors = ButtonDefaults.buttonColors(Color.White),
                ) {
                    FullScreenImage(
                        imageEntity = images[it]
                    )
                }
            }
        } else {
            PhotoDataSetBody(
                images,
                imageViewModel,
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            )

        }
    }
}

/**
 * 图片分组列表
 * @param [imageViewModel]
 * @param [modifier]
 * @param [navHostController]
 */
@Composable
fun ImageGroupList(
    imageViewModel: ImageViewModel,
    modifier: Modifier,
    navHostController: NavHostController
) {
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

@Composable
fun ImportImages(  imageViewModel: ImageViewModel = viewModel(),
                   onDismissRequest: () -> Unit) {
    val checkeds = remember  { mutableStateListOf(false, false, false, false) }

    var currentProgress by remember { mutableFloatStateOf(0f) }
    var loading by remember { mutableStateOf(false) }
    var enabled by remember {
        mutableStateOf(true)
    }
    Dialog(onDismissRequest = onDismissRequest) {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
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
                        }
                    )
                }
                Row {
                    Text(text = "相机", modifier = TEXT_ROW_MODIFIER)
                    Switch(
                        checked = checkeds[1],
                        onCheckedChange = {
                            checkeds[1] = it
                        }
                    )
                }
                Row {
                    Text(text = "腾讯QQ", modifier = TEXT_ROW_MODIFIER)
                    Switch(
                        checked = checkeds[2],
                        onCheckedChange = {
                            checkeds[2] = it
                        }
                    )
                }
                Row {
                    Text(text = "微信", modifier = TEXT_ROW_MODIFIER)
                    Switch(
                        checked = checkeds[3],
                        onCheckedChange = {
                            checkeds[3] = it
                        }
                    )
                }
                if (loading) {
                    LinearProgressIndicator(progress = currentProgress,
                        modifier = Modifier.fillMaxWidth())
                }else{
                    Row(horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()) {
                        OutlinedButton(onClick = {
                            enabled = false
                            loading = true
//                        ThreadPoolManager.getInstance().addTask("imageLoad") {
                            for ((i, path) in SystemApp.IMAGE_PATHS.withIndex()){
                                currentProgress = ((i + 1) / SystemApp.IMAGE_PATHS.size).toFloat()
                                if (checkeds[i]){
                                    imageViewModel.dirList.addAll(
                                        ImageUtils.getDirectoryList(path)
                                    );
                                }
                            }
//                        }
                            enabled = true
                            onDismissRequest()
                        },enabled=enabled) {
                            Text(text = "确定")
                        }
                    }
                }
            }
        }
    }
}
