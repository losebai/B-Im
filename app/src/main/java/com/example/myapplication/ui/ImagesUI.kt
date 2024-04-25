package com.example.myapplication.ui

import android.annotation.SuppressLint
import android.net.Uri
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
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
import com.example.myapplication.common.ImageUtils
import com.example.myapplication.config.PageRouteConfig
import com.example.myapplication.entity.ImageEntity
import com.example.myapplication.viewmodel.ImageViewModel


val ImageModifier: Modifier = Modifier
    .width(100.dp)
    .height(100.dp)
    .padding(1.dp);


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

@Composable
fun PhotoDataSetBody(list: ArrayList<ImageEntity>,imageViewModel: ImageViewModel, mainController: NavHostController , modifier: Modifier = Modifier) {
    val content = LocalContext.current
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp),
        modifier = modifier
            .fillMaxSize()
            .navigationBarsPadding()
    ) {
        items(list.size) { photo ->
            Button(
                onClick = {
                    imageViewModel.imageEntity = list[photo]
                    mainController.navigate(PageRouteConfig.IMAGE_DETAIL_ROUTE)
                },
                modifier = ImageModifier,
                shape = RoundedCornerShape(20),
                colors = ButtonDefaults.buttonColors(Color.White)
            ) {
                Image(
                    painter =
                    rememberAsyncImagePainter(
                        ImageRequest.Builder
                        //淡出效果
                        //圆形效果
                            (content).data(data = list[photo].location)
                            .apply(block = fun ImageRequest.Builder.() {
                                //占位图
                                placeholder(R.drawable.test)
                                //淡出效果
                                crossfade(true)
                                //圆形效果
                            }).build()
                    ),
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
    val list: ArrayList<ImageEntity> = ArrayList()
    val path = imageViewModel.groupPath
    if (path != "") {
        list.addAll(ImageUtils.getImageList(path))
    } else {
        val uri: Uri = Uri.parse("android:resource://drawable/" + R.drawable.test)
        list.add(ImageEntity(null, "test", uri.toString()))
    }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ImageTopBar(imageViewModel.groupName, mainController)
        },
    ) { innerPadding ->
        PhotoDataSetBody(list,imageViewModel, mainController, modifier = Modifier.padding(innerPadding))
    }
}

