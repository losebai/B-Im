package com.example.myapplication.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.myapplication.R
import com.example.myapplication.common.ui.FullScreenImage
import com.example.myapplication.config.PageRouteConfig
import com.example.myapplication.entity.ImageEntity
import com.example.myapplication.viewmodel.ImageViewModel


val ImageModifier: Modifier = Modifier
    .width(100.dp)
    .height(100.dp)
    .padding(1.dp);

var isDetail by mutableStateOf(false)



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
                if (!isDetail){
                    // 从列表页返回
                    mainController.navigateUp()
                }else{
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

@Composable
fun PhotoDataSetBody(
    list: Array<ImageEntity>,
    imageViewModel: ImageViewModel,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 96.dp),
        modifier = modifier
            .fillMaxSize()
            .navigationBarsPadding()
    ) {
        items(list.size) { photo ->
            Button(
                onClick = {
                    imageViewModel.imageDetail = list[photo]
                    isDetail = true
                },
                modifier = ImageModifier,
                shape = RoundedCornerShape(20),
                colors = ButtonDefaults.buttonColors(Color.White)
            ) {
                Image(
                    painter =
                    rememberAsyncImagePainter(list[photo].location),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.padding(0.dp),
                )
            }
        }
    }

}

@Preview(showBackground = true)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoDataSet(
    imageViewModel: ImageViewModel = viewModel(),
    mainController: NavHostController = rememberNavController()
) {
    val path = imageViewModel.groupPath
    if (path != "") {
        imageViewModel.loadPath(path)
    }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        snackbarHost = {
            if (isDetail) {
                SnackbarHost(hostState = snackbarHostState, modifier = Modifier.padding(0.dp))
            }
        },
        topBar = {
            if (isDetail) {
                ImageTopBar(imageViewModel.imageDetail.name, mainController)
            } else {
                ImageTopBar(imageViewModel.groupName, mainController)
            }
        },
        bottomBar = {
            if (isDetail) {
                GetBottomBar(imageViewModel.imageDetail.file)
            }
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    ) { innerPadding ->
        if (isDetail){
            FullScreenImage(imageEntity = imageViewModel.imageDetail, modifier = Modifier.padding(innerPadding).fillMaxSize())
        }else{
            imageViewModel.getImageList(path)
                ?.let {
                    PhotoDataSetBody(
                        it,
                        imageViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
        }
    }
}

