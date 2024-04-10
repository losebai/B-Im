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

class MainActivity : ComponentActivity() {

    private  var  list_one: ListView? = null;
    private var  rg_tab_bar: RadioGroup? = null;
    private var rb_setting: RadioButton? = null;
    private var rb_channel: RadioButton? = null;
    private var rb_better: RadioButton? = null;
    private var rb_message: RadioButton? = null;
    private var txt_topbar: TextView? = null;

    private val appManager: AppManager = AppManager();
    private var vpager : ViewPager2? = null;
    private var homeFragmentPagerAdapter: HomeFragmentPagerAdapter? = null;



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activty_main) // 加载main的布局文件， 也可以动态渲染
        this.bindViews();
    }

    fun bindViews() {
        txt_topbar =  findViewById(R.id.txt_topbar);
        rg_tab_bar = findViewById(R.id.rg_tab_bar);
        rb_channel =  findViewById(R.id.rb_channel);
        rb_message =  findViewById(R.id.rb_message);
        rb_better =  findViewById(R.id.rb_better);
        rb_setting = findViewById(R.id.rb_setting);
        rg_tab_bar?.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.rb_channel) {
                var  intent: Intent =  Intent(this, ImgActivity::class.java);
                startActivity(intent);
            }
        };

        vpager = findViewById(R.id.vpager);
        vpager?.setAdapter(homeFragmentPagerAdapter);
        vpager?.setCurrentItem(0);
        vpager?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
          override fun onPageScrollStateChanged(state: Int){
                if  (state == 2){
                    if (vpager?.currentItem == 1){
                        rb_channel?.isChecked = true;
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