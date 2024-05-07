package com.example.myapplication.common.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Icon
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.myapplication.R
import com.example.myapplication.common.StyleCommon.ZERO_PADDING
import com.example.myapplication.entity.ImageEntity


@Composable
fun FullScreenImage(
    imageEntity: ImageEntity,
    contentDescription: String? = null,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
) {
    Image(
        painter = rememberAsyncImagePainter(
            imageEntity.location
        ),
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = ContentScale.Fit
    )
}
@Composable
fun ImageGroupButton(message: ImageEntity,  onClick: (ImageEntity) -> Unit){
    Button(
        onClick = {
            onClick(message)
        },
        shape = RoundedCornerShape(20),
        modifier = Modifier
            .height(100.dp)
            .width(100.dp),
        contentPadding = ZERO_PADDING,
        colors = ButtonDefaults.buttonColors(Color.White)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = //占位图
                rememberAsyncImagePainter(message.location),
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
fun imagePainter(location: String) =
     rememberAsyncImagePainter(location)

@Composable
fun imagePainter() =
    rememberAsyncImagePainter(
        ImageRequest.Builder
        //淡出效果
        //圆形效果
            (LocalContext.current)
            .apply(block = fun ImageRequest.Builder.() {
                //占位图
                placeholder(R.drawable.test)
                //淡出效果
                crossfade(true)
                //圆形效果
            }).build()
    )

@Composable
fun ImageListView(messages: List<ImageEntity>, onClick: (ImageEntity) -> Unit) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .wrapContentWidth()
            .wrapContentHeight()
    ) {
        messages.forEach { message ->
            ImageGroupButton(message, onClick=onClick)
        }

    }
}
