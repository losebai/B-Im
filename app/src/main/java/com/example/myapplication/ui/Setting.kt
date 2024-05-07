package com.example.myapplication.ui

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.myapplication.R
import com.example.myapplication.common.util.PermissionUtils
import com.example.myapplication.common.util.PermissionsChecker
import com.example.myapplication.common.consts.StyleCommon.ZERO_PADDING
import com.example.myapplication.common.util.Utils
import com.example.myapplication.entity.ImageEntity
import com.example.myapplication.viewmodel.PermissionViewModel


val permissionViewModel: PermissionViewModel = PermissionViewModel()


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview(showBackground = true)
@Composable
fun SettingHome(imageEntity: ImageEntity = ImageEntity()) {
    val scope = rememberCoroutineScope()
    val buttonModifier = Modifier.fillMaxWidth()
    val iconModifier = Modifier.size(25.dp)
    val message = stringResource(id = R.string.empty_ui)
    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxHeight().padding(end = 10.dp)
    ) {
        Column() {
            Row(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
            ) {
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.size(60.dp),
                    shape = RoundedCornerShape(50),
                    contentPadding = ZERO_PADDING,
                    colors = ButtonDefaults.buttonColors(Color.White)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.test),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }
                Column {
                    TextButton(
                        onClick = { /*TODO*/ },
                        contentPadding = ZERO_PADDING,
                    ) {
                        Text(text = "白", fontSize = 20.sp)
                    }
                    Text(
                        text = "已使用10天", fontSize = 10.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 15.dp)
                    )
                }
            }
            IconButton(
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
                        imageVector = Icons.Filled.Home,
                        contentDescription = "Localized description"
                    )
                    Text(
                        text = "  检查权限",
                    )
                }
            }
            IconButton(
                onClick = { Utils.message(scope, message, snackbarHostState) },
                modifier = buttonModifier
            ) {
                Row(
                    modifier = buttonModifier.padding(start = 10.dp, top = 0.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = iconModifier,
                        imageVector = Icons.Filled.Home,
                        contentDescription = "Localized description"
                    )
                    Text(
                        text = "  我的设置",
//                        fontSize = 12.sp,
                    )
                }
            }
            IconButton(
                onClick = { Utils.message(scope, message, snackbarHostState) },
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
                        text = "  意见反馈",
//                        fontSize = 12.sp,
                    )
                }
            }
            IconButton(
                onClick = { Utils.message(scope, message, snackbarHostState) },
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
                        text = "  夜间模式",
//                        fontSize = 12.sp,
                    )
                }
            }
            IconButton(
                onClick = { Utils.message(scope, message, snackbarHostState) },
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
    }
    if (permissionViewModel.isCheck) {
        CheckPermissionDialog()
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview(showBackground = true)
@Composable
fun CheckPermissionDialog() {
    val permissionChecker: PermissionsChecker = PermissionsChecker(LocalContext.current)
    Dialog(onDismissRequest = { permissionViewModel.isCheck = false }) {
        Box(modifier = Modifier
            .padding(10.dp)
            .background(Color.White)) {
            Column(
                Modifier.padding(10.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(id = R.string.checkout_permission), fontSize = 20.sp)
                }
                Row( modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = "写入修改文件权限", fontSize = 12.sp)
                    if (!permissionChecker.lacksPermission(Manifest.permission.MANAGE_EXTERNAL_STORAGE)) {
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
                            PermissionUtils.CheckPermission(
                                arrayOf(
                                    Manifest.permission.MANAGE_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                                )
                            )
                        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            PermissionUtils.CheckPermission(
                                arrayOf(
                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_PHONE_STATE
                                )
                            )
                        }
                    }
                }
                Row(modifier = Modifier.fillMaxWidth() , verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = "读取相册权限", fontSize = 12.sp)
                    if (!permissionChecker.lacksPermissions(
                            Manifest.permission.CAMERA,
                            Manifest.permission.READ_MEDIA_IMAGES
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
                        PermissionUtils.CheckPermission(
                            arrayOf(
                                Manifest.permission.CAMERA,
                                Manifest.permission.READ_MEDIA_IMAGES
                            )
                        )
                    }
                }
                Row(modifier = Modifier.fillMaxWidth() , verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = "获取分享应用列表权限", fontSize = 12.sp)
                    if (!permissionChecker.lacksPermissions(Manifest.permission.QUERY_ALL_PACKAGES)) {
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
                        PermissionUtils.CheckPermission(
                            arrayOf(
                                Manifest.permission.QUERY_ALL_PACKAGES
                            )
                        )
                    }
                }
                Row(modifier = Modifier.fillMaxWidth() , verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = "网络权限", fontSize = 12.sp)
                    if (!permissionChecker.lacksPermissions(Manifest.permission.ACCESS_NETWORK_STATE)) {
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
                        PermissionUtils.CheckPermission(Manifest.permission.ACCESS_NETWORK_STATE)
                    }
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement =Arrangement.Center){
                    Text(text = stringResource(id = R.string.checkout_permission_close), color = Color.Gray)
                }
            }
        }

    }
}
