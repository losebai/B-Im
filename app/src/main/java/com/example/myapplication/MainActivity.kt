package com.example.myapplication

import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.common.ImageUtils
import com.example.myapplication.common.ui.ImageListView
import com.example.myapplication.config.MenuRouteConfig
import com.example.myapplication.config.PageRouteConfig
import com.example.myapplication.entity.ImageEntity
import com.example.myapplication.ui.ImageDetail
import com.example.myapplication.ui.PhotoDataSet
import com.example.myapplication.viewmodel.ImageViewModel

val appBase: AppBase = AppBase()


class MainActivity : AppCompatActivity() {


    lateinit var mainController: NavHostController;

    lateinit var imageViewModel: ImageViewModel;

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            imageViewModel = viewModel<ImageViewModel>()
            ImageUtils.check(LocalContext.current, this)
            mainController = rememberNavController();
            NavHost(
                navController = mainController,
                startDestination = PageRouteConfig.MENU_ROUTE,
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .fillMaxHeight(1f)

            ) {
                composable(PageRouteConfig.MENU_ROUTE) {
                    PageHost()
                }
                composable(PageRouteConfig.IMAGE_PAGE_ROUTE) {
                    PhotoDataSet(imageViewModel, mainController)
                }
                composable(PageRouteConfig.IMAGE_DETAIL_ROUTE) {
                    ImageDetail(imageViewModel.imageEntity, mainController)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @Preview(showBackground = true)
    @Composable
    fun PageHost() {
        appBase.navHostController = rememberNavController();
        appBase.Context(content = { innerPadding ->
            val mod =  Modifier
                .padding(innerPadding)
            NavHost(
                navController = appBase.navHostController,
                startDestination = MenuRouteConfig.ROUTE_IMAGE,
                modifier = mod
                    .fillMaxWidth(1f)
                    .fillMaxHeight(1f)
            ) {
                composable(MenuRouteConfig.ROUTE_IMAGE) {
                    ScaffoldExample()
                }
                composable(MenuRouteConfig.ROUTE_COMMUNITY) {
                    Community(mod)
                }
                composable(MenuRouteConfig.ROUTE_SETTING) {
                    Text(text = "设置", modifier = mod)
                    ImageUtils.CheckPermission()
                }
            }
        })
    }

    @Composable
    @Preview(showBackground = true)
    fun ScaffoldExample(modifier: Modifier = Modifier) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 128.dp),
            modifier = modifier
                .fillMaxSize()
                .navigationBarsPadding()
        ) {
            if (!imageViewModel.isInit) {
                imageViewModel.groupList.addAll(ImageUtils.getDirectoryList(ImageUtils.cameraDirPath));
                imageViewModel.groupList.addAll(ImageUtils.getDirectoryList(ImageUtils.galleryDirPath));
            }
            imageViewModel.isInit = true
            items(imageViewModel.groupList.size) { photo ->

            }
        }
        Column(
            modifier = modifier
                .padding(15.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) { ->
            if (!imageViewModel.isInit) {
                imageViewModel.groupList.addAll(ImageUtils.getDirectoryList(ImageUtils.cameraDirPath));
                imageViewModel.groupList.addAll(ImageUtils.getDirectoryList(ImageUtils.galleryDirPath));
            }
            imageViewModel.isInit = true
            var count = 0;
            imageViewModel.groupList.forEach { _ ->
                count++
                if (count % 3 == 0) {
                    ImageListView(
                        imageViewModel.groupList
                            .subList(count - 3, count)
                    ) { item ->
                        if (item.isDir) {
                            imageViewModel.groupName = item.name
                            imageViewModel.groupPath = item.file?.parent.toString()
                        }
                        mainController.navigate(PageRouteConfig.IMAGE_PAGE_ROUTE)
                    }
                }
            }
            if (count % 3 > 0) {
                ImageListView(
                    imageViewModel.groupList.subList(
                        if (count < 3) 0 else count - 3,
                        count
                    )
                ) { item ->
                    if (item.isDir) {
                        imageViewModel.groupName = item.name
                        imageViewModel.groupPath = item.file?.parent.toString()
                    }
                    mainController.navigate(PageRouteConfig.IMAGE_PAGE_ROUTE)
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





