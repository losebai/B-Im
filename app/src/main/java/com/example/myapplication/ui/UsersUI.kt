package com.example.myapplication.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.myapplication.common.consts.StyleCommon
import com.example.myapplication.common.ui.HeadImage
import com.example.myapplication.common.util.Utils
import com.example.myapplication.entity.UserEntity

@Composable
fun SearchUser(username: String, modifier: Modifier = Modifier,
               onValueChange: (String) -> Unit, title: String = "搜索") =
    OutlinedTextField(value = username,
        placeholder  = { Text(text = title, fontSize = 12.sp) },
        prefix = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Localized description",
                modifier=Modifier.padding(1.dp).size(20.dp)
            )
        },
        onValueChange = onValueChange,
        modifier= modifier
    )


@Composable
fun AddUser() {


}


@Composable
fun UserList(
    userImages: List<UserEntity>,
    modifier: Modifier = Modifier,
    onClick: (UserEntity) -> Unit
) {
    LazyColumn(modifier.fillMaxSize()) {
        items(userImages.size) {
            Button(
                onClick = {
                    onClick(userImages[it])
                },
                shape= StyleCommon.ZERO_SHAPE,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(Color.White)
            ) {
                Row {
                    HeadImage(userImages[it]){
                    }
                    Column(modifier = Modifier.padding(start = 10.dp)) {
                        Text(
                            text = userImages[it].name, modifier = Modifier
                                .fillMaxWidth()
                                ,
                            color = Color.Black, fontSize = 20.sp
                        )
                        Text(
                            text = Utils.stringOrNull(userImages[it].note),
                            fontSize = 14.sp,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }

}

@Composable
fun HeadPhoto(userEntity: UserEntity=Utils.randomUser(), onClick: () -> Unit,){
    Surface(onClick = onClick) {
        Image(
            painter =
            rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(Utils.stringOrNull(userEntity.imageUrl))
                    .size(100)
                    .build()
            ),
            alignment = Alignment.Center,
            contentScale = ContentScale.Fit,
            contentDescription = null,
            modifier = Modifier
                .width(60.dp)
                .height(60.dp)
        )
    }
}
