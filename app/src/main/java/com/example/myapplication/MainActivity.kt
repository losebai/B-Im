package com.example.myapplication

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.myapplication.common.PaddingCommon
import com.example.myapplication.config.RouteConfig
import com.example.myapplication.entity.ImageEntity

val appBase: AppBase = AppBase()


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PageHost()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PageHost() {
    appBase.navHostController = rememberNavController();
    appBase.Context{innerPadding->
        NavHost(navController = appBase.navHostController, startDestination = RouteConfig.ROUTE_IMAGE_PAGE) {
            composable(RouteConfig.ROUTE_IMAGE_PAGE) {
                ScaffoldExample(Modifier.padding(innerPadding))
            }
            composable(RouteConfig.ROUTE_COMMUNITY_PAGE) {
                Community(Modifier.padding(innerPadding))
            }
            composable(RouteConfig.ROUTE_SETTING_PAGE) {
                Text(text = "设置",modifier=Modifier.padding(innerPadding))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Community(modifier: Modifier= Modifier) {
//    appBase.Context { innerPadding ->
        Card(
            modifier = modifier
//                .padding(innerPadding)
                .fillMaxWidth()
                .padding(15.dp), // 外边距
            colors = CardDefaults.cardColors(Color.White),
            elevation = CardDefaults.cardElevation(10.dp)
            // 设置点击波纹效果，注意如果 CardDemo() 函数不在 MaterialTheme 下调用
            // 将无法显示波纹效果
        ) {
            Column(modifier = Modifier.padding(15.dp) // 内边距
                     ) {
                Text(
                    buildAnnotatedString {
                        append("欢迎来到 ")
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.W900,
                                color = Color(0xFF4552B8)
                            )
                        ) {
                            append("Jetpack Compose 博物馆")
                        }
                    }
                )
                val list = ArrayList<ImageEntity>()
                list.add(
                    ImageEntity(
                        null,
                        "name",
                        "file:\\java_items\\images\\app\\src\\main\\res\\drawable\\test.jpg"
                    )
                );
                list.add(
                    ImageEntity(
                        null,
                        "name",
                        "file:\\java_items\\images\\app\\src\\main\\res\\drawable\\test.jpg"
                    )
                );
                ImageListView(list)
            }
//        }
    }
}

@Composable
@Preview(showBackground = true)
fun ScaffoldExample(modifier: Modifier= Modifier) {
//    appBase.Context { innerPadding ->
        Column(
            modifier = modifier
//                .padding(innerPadding)
                .padding(15.dp)
            ,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            val list = ArrayList<ImageEntity>()
            list.add(
                ImageEntity(
                    null,
                    "name",
                    "file:\\java_items\\images\\app\\src\\main\\res\\drawable\\test.jpg"
                )
            );
            list.add(
                ImageEntity(
                    null,
                    "name",
                    "file:\\java_items\\images\\app\\src\\main\\res\\drawable\\test.jpg"
                )
            );
            ImageListView(list)
            Text(text = "推荐")
        }
//    }
}

@Composable
fun ImageListView(messages: List<ImageEntity>) {
    val content = LocalContext.current
    Row() {
        messages.forEach { message ->
            Button(
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(20),
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp),
                contentPadding = PaddingCommon.ZERO_PADDING,
                colors = ButtonDefaults.buttonColors(Color.White)
            ) {
                Column(horizontalAlignment=Alignment.CenterHorizontally) {
                    Image(
                        painter = //占位图
                        rememberAsyncImagePainter(
                            ImageRequest.Builder
                            //淡出效果
                            //圆形效果
                                (content).data(data = message.location)
                                .apply(block = fun ImageRequest.Builder.() {
                                    //占位图
                                    placeholder(R.drawable.test)
                                    //淡出效果
                                    crossfade(true)
                                    //圆形效果
                                }).build()
                        ),
                        alignment = Alignment.Center,
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                        modifier = Modifier
                            .height(80.dp)
                            .padding(5.dp)
                    )
                    Text(text = message.name,
                        color = Color.Black)
                }
            }
        }
    }

}


