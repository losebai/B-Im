package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.activity.AppManager
import com.example.myapplication.activity.ImgActivity
import com.example.myapplication.adapter.HomeFragmentPagerAdapter
import com.example.myapplication.adapter.MyAdapter
import com.example.myapplication.ui.theme.MyApplicationTheme
import java.util.LinkedList

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
        homeFragmentPagerAdapter = HomeFragmentPagerAdapter(this);
        this.bindViews();
        rb_channel?.isChecked = true;
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
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}