package com.example.myapplication

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.common.PaddingCommon
import com.example.myapplication.config.MenuRouteConfig


class AppBase {

    lateinit var navHostController: NavHostController;

    var Page by mutableStateOf(MenuRouteConfig.ROUTE_IMAGE)


    @Composable
    @Preview(showBackground = true)
    @OptIn(ExperimentalMaterial3Api::class)
    fun Get_TopAppBar() {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            title = {
                Row {
                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier.size(40.dp),
                        shape = RoundedCornerShape(50),
                        contentPadding = PaddingCommon.ZERO_PADDING,
                        colors = ButtonDefaults.buttonColors(Color.White)
                    ) {
                        Surface(
                            shape = CircleShape,
                            border = BorderStroke(0.dp, Color.Gray)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.test),
                                contentDescription = null,
                                modifier = Modifier.size(40.dp),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                    TextButton(
                        onClick = { /*TODO*/ },
                        contentPadding = PaddingCommon.ZERO_PADDING,
                    ) {
                        Text(text = "白")
                    }
                }
            }
        )

    }

    @Preview(showBackground = true)
    @Composable
    fun GetBottomBar() {
        BottomAppBar(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.primary,
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val buttonModifier = Modifier.size(70.dp)
                val IconModifier = Modifier.size(30.dp)
                IconButton(
                    onClick = { Page = MenuRouteConfig.ROUTE_IMAGE },
                    modifier = buttonModifier
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            modifier = IconModifier,
                            imageVector = Icons.Filled.Home,
                            contentDescription = "Localized description"
                        )
                        Text(
                            text = "相册",
                            fontSize = 12.sp,
                        )
                    }
                }
                IconButton(
                    onClick = { Page =  MenuRouteConfig.ROUTE_COMMUNITY },
                    modifier = buttonModifier
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            modifier = IconModifier,
                            imageVector = Icons.Filled.AccountBox,
                            contentDescription = "Localized description"
                        )
                        Text(text = "社区", fontSize = 12.sp)
                    }
                }
                IconButton(
                    onClick = { Page = MenuRouteConfig.ROUTE_SETTING },
                    modifier = buttonModifier
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            modifier = IconModifier,
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "Localized description"
                        )
                        Text(text = "设置", fontSize = 12.sp)
                    }
                }
            }
        }
    }


    @Composable
    fun Context(
        content: @Composable (PaddingValues) -> Unit,
        topBar: @Composable () -> Unit = { Get_TopAppBar() },
        bottomBar: @Composable () -> Unit = { GetBottomBar() },
    ) {
        var presses by remember { mutableIntStateOf(0) }
        Scaffold(
            topBar = {                topBar()
            },
            bottomBar = {
                bottomBar()
            },
            floatingActionButton = {
                FloatingActionButton(onClick = { presses++ }) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            content = content
        )
    }


}
