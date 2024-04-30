package com.example.myapplication

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.R
import com.example.myapplication.common.ui.FullScreenImage
import com.example.myapplication.common.ui.ImageListView
import com.example.myapplication.entity.ImageEntity
import com.example.myapplication.ui.GetBottomBar
import com.example.myapplication.ui.ImageDetail
import java.io.File

@Composable
@Preview(showBackground = true)
fun TestFullScreenImage() {
    FullScreenImage(
        ImageEntity(null,null, "D:/java_items/images/app/src/main/res/drawable/test.jpg"
        ), modifier = Modifier.fillMaxSize()
    )
}

@Composable
//@Preview(showBackground = true)
fun TestImageListView(){
    val list: ArrayList<ImageEntity> = ArrayList()
    val uri: Uri = Uri.parse("android:resource://drawable/" + R.drawable.test)
    list.add(ImageEntity(null, "test", uri.toString()))
    list.add(ImageEntity(null, "test", uri.toString()))
    list.add(ImageEntity(null, "test", uri.toString()))
    list.add(ImageEntity(null, "test", uri.toString()))
    list.add(ImageEntity(null, "test", uri.toString()))
    list.add(ImageEntity(null, "test", uri.toString()))
    ImageListView(list){}
}

@Composable
//@Preview(showBackground = true)
fun TestGetBottomBar(){
    GetBottomBar(File("")){}
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
    ImageDetail(ImageEntity(File(uri.toString())), mainController)
}