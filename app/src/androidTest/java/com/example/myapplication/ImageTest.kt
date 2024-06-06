package com.example.myapplication

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toFile
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.common.ui.FullScreenImage
import com.example.myapplication.common.ui.ImageListView
import com.example.myapplication.common.ui.LOADING_MORE
import com.example.myapplication.common.ui.LoadingIndicator
import com.example.myapplication.common.ui.MySwipeRefresh
import com.example.myapplication.common.ui.MySwipeRefreshState
import com.example.myapplication.common.ui.NORMAL
import com.example.myapplication.common.ui.REFRESHING
import com.example.myapplication.dto.FileEntity
import com.example.myapplication.ui.GetBottomBar
import com.example.myapplication.ui.ImageDetail
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File

@Composable
@Preview(showBackground = true)
fun TestFullScreenImage() {
    val uri: Uri = Uri.parse("android:resource://drawable/" + R.drawable.test)
    FullScreenImage(
        FileEntity(uri.toFile()), modifier = Modifier.fillMaxSize()
    )
}

@Composable
//@Preview(showBackground = true)
fun TestImageListView(){
    val list: ArrayList<FileEntity> = ArrayList()
    val uri: Uri = Uri.parse("android:resource://drawable/" + R.drawable.test)
    list.add(FileEntity(uri.toFile()))
    list.add(FileEntity(uri.toFile()))
    list.add(FileEntity(uri.toFile()))
    list.add(FileEntity(uri.toFile()))
    list.add(FileEntity(uri.toFile()))
    list.add(FileEntity(uri.toFile()))
    list.add(FileEntity(uri.toFile()))
    ImageListView(list){}
}

@Composable
//@Preview(showBackground = true)
fun TestGetBottomBar(){
    GetBottomBar(""){}
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
//@Preview(showBackground = true, name = "TestImageShare", group = "test")
fun TestImageShare() {
    // 分享
    val sheetState = rememberModalBottomSheetState();
    var visible by remember {
        mutableStateOf(false)
    }
    Row {
        Button(onClick = {visible = !visible
        }) {
            Text(text = "打开底部弹出")
        }
    }
    if (visible) {
        ModalBottomSheet(
            onDismissRequest = {
                visible = false
            },
            sheetState = sheetState
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter =
                    painterResource(id = R.drawable.test),
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier
                        .height(80.dp)
                        .padding(5.dp)
                )
                Text(
                    text = "微信",
                    color = Color.Black
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun TestImageDetail(){
    val uri: Uri = Uri.parse("android:resource://drawable/" + R.drawable.zi)
    val mainController = rememberNavController();
    ImageDetail(FileEntity(File(uri.toString())), mainController)
}


@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun Test() {
    val scope = rememberCoroutineScope()
    val state by mutableStateOf(MySwipeRefreshState(NORMAL))

    var list by remember {
        mutableStateOf(List(40) { "I'm item $it" })
    }

    MySwipeRefresh(
        state = state,
        indicator = { modifier, s, indicatorHeight ->
            LoadingIndicator(modifier, s, indicatorHeight)
        },
        onRefresh = {
            scope.launch {
                state.loadState = REFRESHING
                //模拟网络请求
                delay(200)
                list = List(20) { "I'm item $it" }
                state.loadState = NORMAL
            }
        },
        onLoadMore = {
            scope.launch {
                state.loadState = LOADING_MORE
                //模拟网络请求
                delay(200)
                list = list + List(20) { "I'm item ${it + list.size}" }
                state.loadState = NORMAL
            }
        }
    ) { modifier ->
        //注意这里要把modifier设置过来，要不然LazyColumn不会跟随它上下拖动
        LazyColumn(modifier) {
            items(items = list, key = { it }) {
                Text(
                    text = it,
                    Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )
            }
        }
    }
}