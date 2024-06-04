package com.example.myapplication.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.common.ui.HeadImage
import com.example.myapplication.entity.UserEntity
import com.example.myapplication.remote.entity.AppUserEntity

object UserInfoUI {

    private val rowModifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)

    @Composable
    fun UserInfoEdit(
        appUserEntity: UserEntity,
        mainController: NavHostController
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            IconButton(onClick = {
                mainController.navigateUp()
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Localized description"
                )
            }
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                HeadImage(
                    appUserEntity.imageUrl,
                    modifier = Modifier.size(100.dp)
                ) {
                }
            }
            Row(
                rowModifier,
                horizontalArrangement = Arrangement.Absolute.SpaceBetween
            ) {
                Text(text = "昵称", fontSize = 20.sp)
                Text(text = appUserEntity.name)
                Text(text = ">", modifier = Modifier.offset(0.dp, (-1).dp))
            }
            Row(rowModifier, horizontalArrangement = Arrangement.Absolute.SpaceBetween) {
                Text(text = "用户ID", fontSize = 20.sp)
                Text(text = appUserEntity.id.toString() ?: "")
            }
            Row(rowModifier, horizontalArrangement = Arrangement.Absolute.SpaceBetween) {
                Text(text = "签名", fontSize = 20.sp)
                Text(text = appUserEntity.note)
                Text(text = ">", modifier = Modifier.offset(0.dp, (-1).dp))
            }
        }
    }
}