package com.example.myapplication.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.common.PaddingCommon
import com.example.myapplication.common.Utils
import com.example.myapplication.entity.ImageEntity
import kotlin.math.absoluteValue


@Preview(showBackground = true)
@Composable
fun SettingHome(imageEntity: ImageEntity = ImageEntity()) {
    val scope = rememberCoroutineScope()
    val buttonModifier = Modifier.fillMaxWidth()
    val IconModifier = Modifier.size(25.dp)
    val message = stringResource(id = R.string.empty_ui)
    Box(modifier = Modifier.background(Color.White).fillMaxHeight()){
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
                    contentPadding = PaddingCommon.ZERO_PADDING,
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
                        contentPadding = PaddingCommon.ZERO_PADDING,
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
                onClick = { },
                modifier = buttonModifier
            ) {
                Row(
                    modifier = buttonModifier.padding(start = 10.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = IconModifier,
                        imageVector = Icons.Filled.Home,
                        contentDescription = "Localized description"
                    )
                    Text(
                        text = "  检查权限",
                    )
                }
            }
            IconButton(
                onClick = { Utils.message(scope, message, snackbarHostState)},
                modifier = buttonModifier
            ) {
                Row(
                    modifier = buttonModifier.padding(start = 10.dp, top = 0.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = IconModifier,
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
                onClick = { Utils.message(scope, message, snackbarHostState)},
                modifier = buttonModifier
            ) {
                Row(
                    modifier = buttonModifier.padding(start = 10.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = IconModifier,
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
                onClick = { Utils.message(scope, message, snackbarHostState)},
                modifier = buttonModifier
            ) {
                Row(
                    modifier = buttonModifier.padding(start = 10.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = IconModifier,
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
                onClick = { Utils.message(scope, message, snackbarHostState)},
                modifier = buttonModifier
            ) {
                Row(
                    modifier = buttonModifier.padding(start = 10.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = IconModifier,
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

}
