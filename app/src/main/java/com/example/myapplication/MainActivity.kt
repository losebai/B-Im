package com.example.myapplication

import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.viewpager2.widget.ViewPager2
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.myapplication.activity.AppManager
import com.example.myapplication.adapter.HomeFragmentPagerAdapter
import com.example.myapplication.common.PaddingCommon
import com.example.myapplication.config.RouteConfig
import com.example.myapplication.entity.ImageEntity

class MainActivity : AppCompatActivity() {

    private var rg_tab_bar: RadioGroup? = null;
    private var rb_setting: RadioButton? = null;
    private var rb_channel: RadioButton? = null;
    private var rb_better: RadioButton? = null;
    private var rb_message: RadioButton? = null;
    private var txt_topbar: TextView? = null;

    private val appManager: AppManager = AppManager();
    private var vpager: ViewPager2? = null;
    private var homeFragmentPagerAdapter: HomeFragmentPagerAdapter? = null;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activty_main) // 加载main的布局文件， 也可以动态渲染
//        homeFragmentPagerAdapter = HomeFragmentPagerAdapter(this);
//        this.bindViews();
//        rb_channel?.isChecked = true;
    }

    fun bindViews() {
        txt_topbar = findViewById(R.id.txt_topbar);
        rg_tab_bar = findViewById(R.id.rg_tab_bar);
        rb_channel = findViewById(R.id.rb_channel);
        rb_message = findViewById(R.id.rb_message);
        rb_better = findViewById(R.id.rb_better);
        rb_setting = findViewById(R.id.rb_setting);
        rg_tab_bar?.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.rb_channel) {
                vpager?.currentItem = 0;
//                val intent: Intent =  Intent(this, ImgActivity::class.java);
//                startActivity(intent);
            } else if (checkedId == R.id.rb_message) {
                vpager?.currentItem = 1;
            } else if (checkedId == R.id.rb_better) {
                vpager?.currentItem = 2;
            } else if (checkedId == R.id.rb_setting) {
                vpager?.currentItem = 3;
            }
        };

        vpager = findViewById(R.id.vpager);
        vpager?.adapter = homeFragmentPagerAdapter;
        vpager?.currentItem = 0;
        vpager?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                //state的状态有三个，0表示什么都没做，1正在滑动，2滑动完毕
                if (state == 2) {
                    if (vpager?.currentItem == 0) {
                        rb_channel?.isChecked = true;
                    } else if (vpager?.currentItem == 1) {
                        rb_message?.isChecked = true;
                    } else if (vpager?.currentItem == 2) {
                        rb_better?.isChecked = true;
                    } else if (vpager?.currentItem == 3) {
                        rb_setting?.isChecked = true;
                    }
                }
            }
        })
    }


}

@Composable
fun PageHost(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = RouteConfig.ROUTE_COMMUNITY_PAGE) {
        composable(RouteConfig.ROUTE_IMAGE_PAGE) {
            ScaffoldExample()
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun Home(){


}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun ScaffoldExample() {
    var presses by remember { mutableIntStateOf(0) }
    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
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
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    var visible by remember {
                        mutableStateOf(false)
                    }
                    AnimatedVisibility(visible = visible) {
                        TextField(
                            value = "",
                            onValueChange = { },
                            label = { }
                        )
                    }
                    IconButton(onClick = { visible = !visible }) {
                        Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = "Localized description"
                        )
                    }
                    IconButton(onClick = { visible = !visible }) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Localized description"
                        )
                    }
                    IconButton(onClick = { visible = !visible }) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Localized description"
                        )
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { presses++ }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
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
        }
    }
}

@Composable
fun ImageListView(messages: List<ImageEntity>) {
    Column(modifier = Modifier
        .padding(top = 20.dp, start=10.dp)
    ) {
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
                    Image(
                        painter = //占位图
                        rememberAsyncImagePainter(
                            ImageRequest.Builder
                            //淡出效果
                            //圆形效果
                                (LocalContext.current).data(data = message.location)
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
                            .size(100.dp)
                            .height(100.dp)
                            .padding(5.dp)
                    )
                    Text(text = "分组")
                }
            }
        }

    }
}


