package com.items.bim

import android.widget.ImageButton
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.items.bim.common.ui.ImageButton
import com.items.bim.config.PageRouteConfig
import com.items.bim.ui.EmailRegister
import com.items.bim.ui.UserLoginBox
import com.items.bim.ui.UserPassWord
import com.items.bim.viewmodel.UserLoginModel


@Composable
@Preview(showBackground = true)
fun UserLoginTest(){
    UserLoginBox(body = {
        EmailRegister()
    })
}

@Preview(showBackground = true)
@Composable
fun UserPassWordTest(mainController : NavHostController = rememberNavController()){
    UserLoginBox(body = {
        UserPassWord(UserLoginModel())
    }, bottom = {
        Column(horizontalAlignment = Alignment.CenterHorizontally,) {
            IconButton(onClick = {
                mainController.navigate(PageRouteConfig.IMAGE_GROUP_LIST)
            }){
                Icon(imageVector = Icons.Outlined.Add, contentDescription = null)
            }
            Text(text = "注册")
        }
    })
}