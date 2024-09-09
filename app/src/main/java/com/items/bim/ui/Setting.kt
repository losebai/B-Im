package com.items.bim.ui

import android.Manifest
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.items.bim.BuildConfig
import com.items.bim.R
import com.items.bim.common.consts.ConfigKey
import com.items.bim.common.consts.StyleCommon.ZERO_PADDING
import com.items.bim.common.consts.SystemApp.snackBarHostState
import com.items.bim.common.ui.AppBarButton
import com.items.bim.common.ui.IconRowButton
import com.items.bim.common.ui.TopAppBarBack
import com.items.bim.common.util.CheckPermission
import com.items.bim.common.util.PermissionsChecker
import com.items.bim.common.util.QQUtils
import com.items.bim.common.util.Utils
import com.items.bim.config.PageRouteConfig
import com.items.bim.config.WEB_API_ROURE
import com.items.bim.entity.UserEntity
import com.items.bim.viewmodel.ConfigViewModel
import com.items.bim.viewmodel.HomeViewModel
import com.items.bim.viewmodel.PermissionViewModel
import com.items.bim.viewmodel.UserLoginModel


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun SettingHome(
    userEntity: UserEntity = UserEntity(),
    mainController: NavHostController = rememberNavController(),
    homeViewModel: HomeViewModel = viewModel()
) {
    val permissionViewModel: PermissionViewModel = viewModel()
    val buttonModifier = Modifier.fillMaxWidth()
    val iconModifier = Modifier.size(25.dp)
    val bottomModifier = Modifier.size(50.dp)
    Box(
        modifier = Modifier
            .background(Color.White)
            .padding(end = 10.dp)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.SpaceBetween) {
            Column {
                Row(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            mainController.navigate(PageRouteConfig.USER_INFO)
                        },
                        modifier = Modifier
                            .size(60.dp)
                            ,
                        contentPadding = ZERO_PADDING,
                        colors = ButtonDefaults.buttonColors(Color.White)
                    ) {
                        Surface(
                            shape = CircleShape,
                            border = BorderStroke(0.dp, Color.Gray)
                        ) {
                            AsyncImage(
                                userEntity.imageUrl,
                                contentDescription = null,
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                    Column(
                        Modifier
                            .padding(10.dp)
                    ) {
                        TextButton(
                            onClick = { /*TODO*/ },
                            contentPadding = ZERO_PADDING,
                        ) {
                            Text(text = userEntity.name, fontSize = 20.sp)
                        }
                        Text(
                            text = userEntity.note, fontSize = 10.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(start = 15.dp)
                        )
                    }
                }

                TextButton(
                    onClick = {
                        permissionViewModel.isCheck = true
                    },
                    modifier = buttonModifier
                ) {
                    Row(
                        modifier = buttonModifier.padding(start = 10.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = iconModifier,
                            imageVector = Icons.Filled.Build,
                            contentDescription = "Localized description"
                        )
                        Text(
                            text = "  检查权限",
                        )
                    }
                }

                TextButton(
                    onClick = { QQUtils.joinQQGroup("966922403") },
                    modifier = buttonModifier
                ) {
                    Row(
                        modifier = buttonModifier.padding(start = 10.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = iconModifier,
                            imageVector = Icons.Filled.MailOutline,
                            contentDescription = "Localized description"
                        )
                        Text(
                            text = "  意见反馈",
//                        fontSize = 12.sp,
                        )
                    }
                }

                TextButton(
                    onClick = { QQUtils.joinQQGroup("966922403") },
                    modifier = buttonModifier
                ) {
                    Row(
                        modifier = buttonModifier.padding(start = 10.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = iconModifier,
                            imageVector = Icons.Filled.Home,
                            contentDescription = "Localized description"
                        )
                        Text(
                            text = "  加入QQ群",
//                        fontSize = 12.sp,
                        )
                    }
                }
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                AppBarButton(
                    imageVector = Icons.Filled.Settings,
                    text = "设置",
                    modifier = bottomModifier,
                    onClick = {
                        mainController.navigate(PageRouteConfig.SETTING_INDEX)
                              },
                )
                AppBarButton(
                    active = homeViewModel.darkTheme,
                    painter = painterResource(id = R.drawable.moon),
                    activeColor = Color.Green,
                    text = if (homeViewModel.darkTheme) "白天" else "夜间",
                    modifier = Modifier.size(30.dp),
                    onClick = { homeViewModel.darkTheme = !homeViewModel.darkTheme },
                )
            }
        }
    }
    if (permissionViewModel.isCheck) {
        CheckPermissionDialog(permissionViewModel)
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
//@Preview(showBackground = true)
@Composable
fun CheckPermissionDialog(permissionViewModel: PermissionViewModel = viewModel()) {
    Dialog(onDismissRequest = { permissionViewModel.isCheck = false }) {
        Box(
            modifier = Modifier
                .padding(10.dp)
                .background(Color.White)
        ) {
            Column(
                Modifier.padding(10.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(id = R.string.checkout_permission), fontSize = 20.sp)
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "写入修改文件权限", fontSize = 12.sp)
                    if (PermissionsChecker.getInstance()
                            .lacksPermission(Manifest.permission.MANAGE_EXTERNAL_STORAGE)
                    ) {
                        TextButton(
                            onClick = { permissionViewModel.filePermission = true },
                            modifier = Modifier.padding(0.dp)
                        ) {
                            Text(text = "检查", fontSize = 12.sp, color = Color.Green)
                        }
                    } else {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = null,
                            tint = Color.Green
                        )
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        if (permissionViewModel.filePermission) {
                            CheckPermission(
                                arrayOf(
                                    Manifest.permission.MANAGE_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                                )
                            )
                        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            CheckPermission(
                                arrayOf(
                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_PHONE_STATE
                                )
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "相机摄像头权限", fontSize = 12.sp)
                    if (PermissionsChecker.getInstance().lacksPermissions(
                            arrayOf(
                                Manifest.permission.CAMERA,
                                Manifest.permission.RECORD_AUDIO
                            )
                        )
                    ) {
                        TextButton(
                            onClick = { permissionViewModel.cameraPermission = true },
                            modifier = Modifier.padding(0.dp)
                        ) {
                            Text(text = "检查", fontSize = 12.sp, color = Color.Green)
                        }
                    } else {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = null,
                            tint = Color.Green
                        )
                    }
                    if (permissionViewModel.cameraPermission) {
                        CheckPermission(
                            arrayOf(
                                Manifest.permission.CAMERA,
                                Manifest.permission.RECORD_AUDIO
                            )
                        )
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "读取相册权限", fontSize = 12.sp)
                    if (PermissionsChecker.getInstance().lacksPermissions(
                            arrayOf(
                                Manifest.permission.CAMERA,
                                Manifest.permission.READ_MEDIA_IMAGES
                            )
                        )
                    ) {
                        TextButton(
                            onClick = { permissionViewModel.photoPermission = true },
                            modifier = Modifier.padding(0.dp)
                        ) {
                            Text(text = "检查", fontSize = 12.sp, color = Color.Green)
                        }
                    } else {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = null,
                            tint = Color.Green
                        )
                    }
                    if (permissionViewModel.photoPermission) {
                        CheckPermission(
                            arrayOf(
                                Manifest.permission.CAMERA,
                                Manifest.permission.READ_MEDIA_IMAGES
                            )
                        )
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "获取分享应用列表权限", fontSize = 12.sp)
                    if (PermissionsChecker.getInstance()
                            .lacksPermission(Manifest.permission.QUERY_ALL_PACKAGES)
                    ) {
                        TextButton(
                            onClick = { permissionViewModel.appPermission = true },
                            modifier = Modifier.padding(0.dp)
                        ) {
                            Text(text = "检查", fontSize = 12.sp, color = Color.Green)
                        }
                    } else {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = null,
                            tint = Color.Green
                        )
                    }
                    if (permissionViewModel.appPermission) {
                        CheckPermission(
                            arrayOf(
                                Manifest.permission.QUERY_ALL_PACKAGES
                            )
                        )
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "网络权限", fontSize = 12.sp)
                    if (PermissionsChecker.getInstance()
                            .lacksPermission(Manifest.permission.ACCESS_NETWORK_STATE)
                    ) {
                        TextButton(
                            onClick = { permissionViewModel.networkPermission = true },
                            modifier = Modifier.padding(0.dp)
                        ) {
                            Text(text = "检查", fontSize = 12.sp, color = Color.Green)
                        }
                    } else {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = null,
                            tint = Color.Green
                        )
                    }
                    if (permissionViewModel.networkPermission) {
                        CheckPermission(Manifest.permission.ACCESS_NETWORK_STATE)
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.checkout_permission_close),
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun SettingDetail(userLoginModel: UserLoginModel,
                  configViewModel : ConfigViewModel,
                  mainController: NavHostController){
    TopAppBarBack(
        mainController=mainController, title = {
        Text(text = "设置")
    }){
        Column(modifier=Modifier.padding(10.dp),) {
            TextButton(onClick = {
                userLoginModel.logout()
                mainController.navigate(PageRouteConfig.USER_LOGIN)
            },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(id = R.color.active_button))) {
                Text(text = "退出登录", color = Color.White)
            }
        }
        TextButton(onClick = {
            mainController.navigate(PageRouteConfig.SETTING_ABOUT)
        }) {
            Text(text = "关于", color = Color.White)
        }
    }
}
@Composable
fun About(configViewModel : ConfigViewModel, mainController : NavHostController){
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painterResource(id = R.drawable.login_log),
            contentDescription = "登录跳转页面"
        )
        IconRowButton(imageVector = Icons.Outlined.Info, title = "当前版本", data = BuildConfig.VERSION_NAME)
        IconRowButton(imageVector = Icons.Outlined.Info, title = "版本更新", data = if (configViewModel.getConfig(
                ConfigKey.APP_VERSION, String::class.java) == BuildConfig.VERSION_NAME)  "已是最新版本" else  "需要更新")
        IconRowButton(imageVector = Icons.Outlined.Home, title = "官网", onClick = {
            mainController.navigate(
                WEB_API_ROURE.WEB_ROUTE + "/${
                    Uri.encode(
                        configViewModel.getConfig(
                            ConfigKey.HOME_URL, String::class.java)
                    )
                }"
            )
        })
    }
}