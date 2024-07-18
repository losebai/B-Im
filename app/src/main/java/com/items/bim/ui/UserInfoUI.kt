package com.items.bim.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.items.bim.common.ui.HeadImage
import com.items.bim.config.PageRouteConfig
import com.items.bim.entity.UserEntity


private val rowModifier = Modifier
    .fillMaxWidth()
    .padding(10.dp)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditPage(
    title: String,
    default: String = "",
    mainController: NavHostController,
    onChangedCallback: (String) -> Unit
) {
    var dateString by remember {
        mutableStateOf(default)
    }
    Column(horizontalAlignment = Alignment.Start,
        modifier = Modifier.padding(5.dp)) {
        TopAppBar(title = { Text(text = title, color = Color.Black) }, navigationIcon = {
            IconButton(onClick = {
                mainController.navigateUp()
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "返回"
                )
            }
        },actions={
            OutlinedButton(onClick = {
                mainController.navigateUp()
                onChangedCallback(dateString)
            }, colors = ButtonDefaults.buttonColors(Color.White)) {
                Text(text = "完成",color= Color.Blue)
            }
        })
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(value = dateString,
                modifier = Modifier.fillMaxWidth(0.9f),
                onValueChange = { dateString = it })
            Icon(Icons.Outlined.CheckCircle, null,
                modifier = Modifier.padding(10.dp),)
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserInfoEdit(
    appUserEntity: UserEntity,
    mainController: NavHostController
) {
    Column(
        Modifier
            .fillMaxSize()
//            .padding(10.dp)
    ) {
        TopAppBar(title = {
        }, modifier=Modifier.height(70.dp)
            ,navigationIcon = {
            IconButton(onClick = {
                mainController.navigateUp()
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "返回"
                )
            }
        })
        Row(
            Modifier.fillMaxWidth().padding(bottom = 5.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            HeadImage(
                appUserEntity.imageUrl,
                modifier = Modifier.size(80.dp)
            ) {
                mainController.navigate("${PageRouteConfig.IMAGE_SELECTOR}/headImage")
            }
        }
        Row(
            rowModifier.clickable {
                mainController.navigate(PageRouteConfig.USER_INFO_USERNAME)
            },
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "昵称", fontSize = 20.sp)
            Text(text = appUserEntity.name)
            Text(text = ">", modifier = Modifier.offset(0.dp, (-1).dp))
        }
        Row(rowModifier, horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "用户ID", fontSize = 20.sp)
            Text(text = appUserEntity.id.toString() ?: "")
        }
        Row(
            rowModifier.clickable {
                mainController.navigate(PageRouteConfig.USER_INFO_NOTE)
            }, horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "签名", fontSize = 20.sp)
            Text(text = appUserEntity.note)
            Text(text = ">", modifier = Modifier.offset(0.dp, (-1).dp))
        }
    }
}
