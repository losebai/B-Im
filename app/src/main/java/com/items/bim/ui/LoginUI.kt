package com.items.bim.ui

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.items.bim.R
import com.items.bim.common.consts.StyleCommon
import com.items.bim.common.consts.SystemApp
import com.items.bim.common.ui.buttonClick
import com.items.bim.common.util.ThreadPoolManager
import com.items.bim.common.util.Utils
import com.items.bim.config.PageRouteConfig
import com.items.bim.dto.UserLoginDto
import com.items.bim.entity.toUserEntity
import com.items.bim.viewmodel.UserLoginModel
import com.items.bim.viewmodel.UserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


@Composable
//@Preview(showBackground = true)
fun LoginStart() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            Modifier.height(150.dp),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painterResource(id = R.drawable.login_log), contentDescription = "登录跳转页面")
            Text(text = stringResource(id = R.string.app_desc), fontSize = 18.sp)
        }
    }
}

@Composable
fun UserPassWord(userLoginModel: UserLoginModel, onLogin: (String, String) -> Unit = { u, p -> }) {
    var username by remember {
        mutableStateOf("")
    }
    var usernameClick by remember {
       mutableStateOf(false)
    }
    var password by remember {
        mutableStateOf("")
    }
    var passwordClick by remember {
        mutableStateOf(false)
    }
    Column {
        OutlinedTextField(value = username, onValueChange = {
            username = it
        }, label = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,//设置水平居中对齐
                verticalAlignment = Alignment.CenterVertically//设置垂直居中对齐
            ) {
                Text(
                    text = "请输入账户",
                )
            }

        }, modifier = Modifier.fillMaxWidth().buttonClick {
            usernameClick = true
        },
            isError = username.isEmpty() && usernameClick
        )
        OutlinedTextField(value = password, onValueChange = {
            password = it
        }, label = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,//设置水平居中对齐
                verticalAlignment = Alignment.CenterVertically//设置垂直居中对齐
            ) {
                Text(
                    text = "请输入密码",
                )
            }
        }, modifier = Modifier.fillMaxWidth().buttonClick {
            passwordClick = true
        },
            isError = password.isEmpty() && passwordClick
        )
        TextButton(
            onClick = {
                if (username.isEmpty() || password.isEmpty()){
                    userLoginModel.loginText.value = "请输入账户和密码"
                }else{
                    onLogin(username, password)
                }
                      }, modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 10.dp)
                .background(colorResource(id = R.color.active_button))
        ) {
            Text(text = "登录", color = Color.White)
        }
        AnimatedVisibility(visible = userLoginModel.loginText.value.isNotEmpty()) {
            Text(text = userLoginModel.loginText.value, color = Color.Red)
        }
    }
}

@Composable
fun EmailRegister(onSendCode: (String) -> Unit = {}, onRegister: (UserLoginDto) -> Unit = {}) {
    var name by remember {
        mutableStateOf("")
    }
    var username by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var password2 by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var code by remember {
        mutableStateOf("")
    }
    Column {
        OutlinedTextField(value = name, onValueChange = {
            name = it
        }, label = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,//设置水平居中对齐
                verticalAlignment = Alignment.CenterVertically//设置垂直居中对齐
            ) {
                Text(
                    text = "请输入昵称",
                )
            }
        }, modifier = StyleCommon.inputModifier)
        OutlinedTextField(value = username, onValueChange = {
            username = it
        }, label = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,//设置水平居中对齐
                verticalAlignment = Alignment.CenterVertically//设置垂直居中对齐
            ) {
                Text(
                    text = "请输入账号",
                )
            }
        }, modifier = StyleCommon.inputModifier)
        OutlinedTextField(value = email, onValueChange = {
            email = it
        }, label = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,//设置水平居中对齐
                verticalAlignment = Alignment.CenterVertically//设置垂直居中对齐
            ) {
                Text(
                    text = "请输入邮箱号",
                )
            }

        }, modifier = StyleCommon.inputModifier)
        Row(modifier = StyleCommon.inputModifier) {
            OutlinedTextField(value = code, onValueChange = {
                code = it
            }, label = {
//                Text(
//                    text = "请输入验证码",
//                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,//设置水平居中对齐
                    verticalAlignment = Alignment.CenterVertically//设置垂直居中对齐
                ) {
                    Text(
                        text = "请输入验证码",
                    )
                }
            }, modifier = Modifier.fillMaxWidth(0.77f))
            TextButton(
                onClick = { onSendCode(email) }, modifier = Modifier
                    .padding(top = 10.dp, bottom = 10.dp, start = 10.dp)
                    .background(colorResource(id = R.color.active_button))
            ) {
                Text(text = "发送", color = Color.White)
            }
        }
        OutlinedTextField(value = password, onValueChange = {
            password = it
        }, label = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,//设置水平居中对齐
                verticalAlignment = Alignment.CenterVertically//设置垂直居中对齐
            ) {
                Text(
                    text = "请输入密码",
                )
            }
        }, modifier =StyleCommon.inputModifier)

        OutlinedTextField(value = password2, onValueChange = {
            password2 = it
        }, label = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,//设置水平居中对齐
                verticalAlignment = Alignment.CenterVertically//设置垂直居中对齐
            ) {
                Text(
                    text = "请确认密码",
                )
            }
        },  modifier =StyleCommon.inputModifier)
        TextButton(
            onClick = { onRegister(UserLoginDto(username, password, name,  email, code)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp)
                .height(40.dp)
                .background(colorResource(id = R.color.active_button))
        ) {
            Text(text = "注册", color = Color.White)
        }
    }
}

@Composable
fun UserLoginBox(body: @Composable () -> Unit, bottom: @Composable () -> Unit = {}) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .fillMaxHeight(0.8f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(modifier = Modifier.fillMaxHeight(0.1f)) {
                Image(
                    painterResource(id = R.drawable.login_log),
                    contentDescription = "登录跳转页面"
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight(0.7f)
            ) {

                body()
            }
            Row(
                modifier = Modifier
//                .fillMaxHeight(0.2f)
            ) {
                bottom()
            }
        }
    }
}

@Composable
fun UserRegister(userLoginModel: UserLoginModel, navHostController: NavHostController) {
    UserLoginBox(body = {
        EmailRegister(onSendCode = {
            userLoginModel.userLoginService.sendCode(it)
        },
            onRegister = { dto ->
                userLoginModel.userLoginService.register(dto)
            })
    })
}

@Composable
fun UserLogin(
    userLoginModel: UserLoginModel,
    userViewModel: UserViewModel,
    navHostController: NavHostController
) {
    UserLoginBox(body = {
        UserPassWord(userLoginModel, onLogin = { u, p ->
            try {
                userLoginModel.login(UserLoginDto(u, p)) { isLogin ->
                    if (isLogin) {
                        userViewModel.loadCurUser()
                        MainScope().launch(Dispatchers.Main) {
                            navHostController.navigate(PageRouteConfig.MENU_ROUTE)
                        }
                    } else {
                        Log.e("login", "登录失败")
                        userLoginModel.loginText.value = "登录失败，账户或者密码错误"
                        Utils.message(MainScope(), "登录失败", SystemApp.snackBarHostState)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                userLoginModel.loginText.value = "登录错误 ${e.message}"
                Utils.message(MainScope(), "登录失败", SystemApp.snackBarHostState)
            }
        })
    }, bottom = {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            IconButton(onClick = {
                navHostController.navigate(PageRouteConfig.USER_REGISTER)
            }) {
                Icon(imageVector = Icons.Outlined.Add, contentDescription = null)
            }
            Text(text = "注册")
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            IconButton(onClick = {
                ThreadPoolManager.getInstance().addTask("io") {
                    userViewModel.userEntity = userLoginModel.guestLogin().toUserEntity()
                }
                navHostController.navigate(PageRouteConfig.MENU_ROUTE)
            }) {
                Icon(imageVector = Icons.Outlined.Person, contentDescription = null)
            }
            Text(text = "游客访问")
        }
    })
}