package com.example.myapplication.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.myapplication.common.consts.StyleCommon
import com.example.myapplication.entity.UserEntity


@Composable
fun AddUser() {


}


@Composable
fun UserList(userImages: List<UserEntity>, onClick: (UserEntity) -> Unit) {
    LazyColumn(Modifier.fillMaxSize()) {
        items(userImages.size) {
            Button(
                onClick = {
                    onClick(userImages[it])
                },
                shape= StyleCommon.ZERO_Shape,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                colors = ButtonDefaults.buttonColors(Color.White)
            ) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)) {
                    Image(
                        painter =
                        rememberAsyncImagePainter(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(userImages[it].imageUrl)
                                .size(100)
                                .build()
                           ),
                        alignment = Alignment.Center,
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                        modifier = Modifier
                            .width(80.dp)
                            .height(80.dp)
                            .padding(1.dp)
                            .clip(StyleCommon.ONE_SHAPE)

                    )
                    Column(modifier = Modifier.padding(start = 10.dp)) {
                        Text(text = userImages[it].name,modifier= Modifier
                            .fillMaxWidth()
                            .height(40.dp), color = Color.Black, fontSize = 20.sp)
                        Text(text = userImages[it].note,modifier=Modifier.height(40.dp), color = Color.Black)
                    }
                }
            }
        }
    }

}


