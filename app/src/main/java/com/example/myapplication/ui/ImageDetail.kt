package com.example.myapplication.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.common.ui.FullScreenImage
import com.example.myapplication.config.MenuRouteConfig
import com.example.myapplication.entity.ImageEntity


@Composable
fun ImageDetail(imageEntity: ImageEntity, mainController: NavHostController) {
    Scaffold(
        topBar = {
            ImageTopBar(imageEntity.name, mainController)
        },
        bottomBar = {
            GetBottomBar(mainController)
        }
    ) { innerPadding ->
        FullScreenImage(imageEntity = imageEntity, modifier = Modifier.padding(innerPadding))
    }

}

@Preview(showBackground = true)
@Composable
fun GetBottomBar(navHostController: NavHostController = rememberNavController()) {
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
                onClick = { navHostController.navigate(MenuRouteConfig.ROUTE_IMAGE) },
                modifier = buttonModifier
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        modifier = IconModifier,
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Localized description"
                    )
                    Text(
                        text = "编辑",
                        fontSize = 12.sp,
                    )
                }
            }
            IconButton(
                onClick = { navHostController.navigate(MenuRouteConfig.ROUTE_COMMUNITY) },
                modifier = buttonModifier
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        modifier = IconModifier,
                        imageVector = Icons.Filled.Share,
                        contentDescription = "Localized description"
                    )
                    Text(text = "分享", fontSize = 12.sp)
                }
            }
            IconButton(
                onClick = { navHostController.navigate(MenuRouteConfig.ROUTE_SETTING) },
                modifier = buttonModifier
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        modifier = IconModifier,
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Localized description"
                    )
                    Text(text = "删除", fontSize = 12.sp)
                }
            }
            IconButton(
                onClick = { navHostController.navigate(MenuRouteConfig.ROUTE_SETTING) },
                modifier = buttonModifier
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        modifier = IconModifier,
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = "Localized description"
                    )
                    Text(text = "更多", fontSize = 12.sp)
                }
            }
        }
    }
}