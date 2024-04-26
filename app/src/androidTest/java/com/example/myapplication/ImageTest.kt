package com.example.myapplication

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.common.ui.FullScreenImage
import com.example.myapplication.common.ui.ImageListView
import com.example.myapplication.entity.ImageEntity
import com.example.myapplication.ui.GetBottomBar

@Composable
@Preview(showBackground = true)
fun TestFullScreenImage() {
    val uri: Uri = Uri.parse("android:resource://myapplication/" + R.drawable.test)
    FullScreenImage(
        ImageEntity(null,null,uri.toString()
        )
    )
}

@Composable
@Preview(showBackground = true)
fun TestImageListView(){
    val list: ArrayList<ImageEntity> = ArrayList()
    val uri: Uri = Uri.parse("android:resource://myapplication/" + R.drawable.test)
    list.add(ImageEntity(null, "test", uri.toString()))
    list.add(ImageEntity(null, "test", uri.toString()))
    list.add(ImageEntity(null, "test", uri.toString()))
    list.add(ImageEntity(null, "test", uri.toString()))
    list.add(ImageEntity(null, "test", uri.toString()))
    list.add(ImageEntity(null, "test", uri.toString()))
    ImageListView(list){}
}

@Composable
@Preview(showBackground = true)
fun TestGetBottomBar(){
    GetBottomBar()
}