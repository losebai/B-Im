package com.example.myapplication.common.ui

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.myapplication.R
import com.example.myapplication.common.PaddingCommon
import com.example.myapplication.entity.ImageEntity


@Composable
fun FullScreenImage(
    imageEntity: ImageEntity,
    contentDescription: String? = null,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
) {
    val content = LocalContext.current
    Image(
        painter = rememberAsyncImagePainter(
            ImageRequest.Builder
            //淡出效果
            //圆形效果
                (content).data(data = imageEntity.location)
                .apply(block = fun ImageRequest.Builder.() {
                    //占位图
                    placeholder(R.drawable.test)
                    //淡出效果
                    crossfade(true)
                    //圆形效果
                }).build()
        ),
        contentDescription = contentDescription,
        modifier = modifier
            .fillMaxSize(),
        contentScale = ContentScale.None
    )
}
@Composable
fun ImageGroupButton(message: ImageEntity,content: Context = LocalContext.current,  onClick: (ImageEntity) -> Unit){
    Button(
        onClick = {
            onClick(message)
        },
        shape = RoundedCornerShape(20),
        modifier = Modifier
            .height(100.dp)
            .width(100.dp),
        contentPadding = PaddingCommon.ZERO_PADDING,
        colors = ButtonDefaults.buttonColors(Color.White)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = //占位图
                rememberAsyncImagePainter(ImageRequest.Builder
                //淡出效果
                //圆形效果
                    (content).data(data = message.location)
                    .apply(block = fun ImageRequest.Builder.() {
                        //占位图
                        placeholder(R.drawable.test)
                        //淡出效果
                        crossfade(true)
                        //圆形效果
                    }).build()),
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .height(80.dp)
                    .padding(5.dp)
            )
            Text(
                text = message.name,
                color = Color.Black
            )
        }
    }
}

@Composable
fun ImageListView(messages: List<ImageEntity>, onClick: (ImageEntity) -> Unit) {
    val content = LocalContext.current
    Row(
        modifier = Modifier
            .padding(8.dp)
            .wrapContentWidth()
            .wrapContentHeight()
    ) {
        messages.forEach { message ->
            ImageGroupButton(message, content, onClick=onClick)
        }
    }
}
