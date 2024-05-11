package com.example.myapplication.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.R
import com.example.myapplication.common.consts.StyleCommon
import com.example.myapplication.common.ui.HeadImage
import com.example.myapplication.common.util.Utils
import com.example.myapplication.entity.UserEntity


@Composable
fun CommunityHome(userEntity: UserEntity = Utils.randomUser(), background: String) {
    val lazyListState = rememberLazyListState()
    Column() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    if (userEntity.imageUrl == null) painterResource(id = R.drawable.test)
                    else rememberAsyncImagePainter(background)
                )
        ) {
            HeadImage(onClick = {}, userEntity = userEntity, modifier = Modifier.size(150.dp))
        }
        LazyColumn(state = lazyListState) {

        }
    }
}