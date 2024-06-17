package com.example.myapplication.activity

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.MainActivity
import com.example.myapplication.ui.ComposeTestTheme
import com.example.myapplication.ui.MCRoleLotteryHome
import com.example.myapplication.viewmodel.LotteryViewModel

class LotteryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        this.enableEdgeToEdge()
        setContent {
            ComposeTestTheme {
                val lotteryViewModel by viewModels<LotteryViewModel>()
                MCRoleLotteryHome(lotteryViewModel, onDispatch = {
                    val intent = Intent(baseContext, MainActivity::class.java)
                    startActivity(intent)
                })
            }
        }
    }
}