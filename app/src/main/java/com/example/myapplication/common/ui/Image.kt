package com.example.myapplication.common.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.myapplication.R
import com.example.myapplication.common.consts.StyleCommon
import com.example.myapplication.common.consts.StyleCommon.ZERO_PADDING
import com.example.myapplication.common.util.Utils
import com.example.myapplication.entity.FileEntity
import com.example.myapplication.entity.UserEntity
import com.example.myapplication.remote.entity.toUserEntity


@Composable
fun FullScreenImage(
    fileEntity: FileEntity,
    contentDescription: String? = null,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
) =
    Image(
        painter = rememberAsyncImagePainter(
            fileEntity.location
        ),
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = ContentScale.Fit
    )

@Composable
fun ImageGroupButton(message: FileEntity, onClick: (FileEntity) -> Unit) {
    Button(
        onClick = {
            onClick(message)
        },
        shape = StyleCommon.IMAGE_BUTTON_SHAPE,
        modifier = Modifier
            .height(100.dp)
            .width(100.dp),
        contentPadding = ZERO_PADDING,
    ) {
        Column {
            Image(
                painter = //占位图
                rememberAsyncImagePainter(message.location),
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop,
                contentDescription = null,
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
fun ImageListView(messages: List<FileEntity>, onClick: (FileEntity) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .padding(8.dp)
            .wrapContentWidth()
            .wrapContentHeight()
    ) {
        items(messages) {
            ImageGroupButton(it, onClick = onClick)
        }
    }
}

@SuppressLint("ModifierParameter")
@Composable
fun HeadImage(
    userEntity: UserEntity = Utils.randomUser().toUserEntity(),
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) = Surface(
    onClick = onClick,
    shape = CircleShape,
    border = BorderStroke(0.dp, Color.Gray),
    modifier = modifier
) {
    Image(
        painter = if (userEntity.imageUrl.isEmpty()) painterResource(id = R.drawable.test)
        else rememberAsyncImagePainter(userEntity.imageUrl), contentDescription = null,
        contentScale = ContentScale.Crop
    )
}

@SuppressLint("ModifierParameter")
@Composable
fun HeadImage(
    path: String?,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) =
    Surface(
        onClick = onClick,
        shape = CircleShape,
        border = BorderStroke(0.dp, Color.Gray),
        modifier = modifier
    ) {
        Image(
            painter = rememberAsyncImagePainter(path),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }

