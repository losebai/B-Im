package com.example.myapplication.activity

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.MainActivity
import com.example.myapplication.common.provider.PlayerProvider
import com.example.myapplication.common.util.ThreadPoolManager
import com.example.myapplication.config.MingChaoRoute
import com.example.myapplication.event.GlobalInitEvent
import com.example.myapplication.ui.AwardList
import com.example.myapplication.ui.ComposeTestTheme
import com.example.myapplication.ui.MCRoleLotteryHome
import com.example.myapplication.viewmodel.LotteryViewModel
import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {
}


class LotteryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val lotteryViewModel by viewModels<LotteryViewModel>();
        GlobalInitEvent.run()
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        this.enableEdgeToEdge()
        setContent {
            ComposeTestTheme {
                val navHostController = rememberNavController()
                val gameName = intent.getStringExtra("gameName") ?: ""
                NavHost(
                    navController = navHostController,
                    startDestination = MingChaoRoute.LOTTERY_ROUTE,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    composable(MingChaoRoute.LOTTERY_ROUTE) {
                        MCRoleLotteryHome(
                            gameName,
                            lotteryViewModel, onLottery = { pool, num ->
                                val isUp = 5 >= pool.poolType.value && pool.poolType.value <= 6
                                val catalogueId = if (pool.poolType.value % 2 == 1) 1105 else 1106
                                val poolId = pool.poolId
                                ThreadPoolManager.getInstance().addTask("lottery", "lottery") {
                                    logger.info { "开始抽奖" }
                                    lotteryViewModel.award = lotteryViewModel.randomAward(
                                        gameName, catalogueId, poolId, num, isUp
                                    )
                                }
                                navHostController.navigate(MingChaoRoute.AWARD_LIST)
                            }, onDispatch = {
                                val intent = Intent(baseContext, MainActivity::class.java)
                                startActivity(intent)
                            })
                    }
                    composable(MingChaoRoute.AWARD_LIST) { backStackEntry ->
                        AwardList(
                            Modifier, lotteryViewModel,
                            onDispatch = {
                                navHostController.navigateUp()
                            }
                        )
                    }
                }
            }
        }
    }

    public override fun onPause() {
        super.onPause()
        PlayerProvider.getInstance().release()
    }


    public override fun onStop() {
        super.onStop()
        PlayerProvider.getInstance().release()
    }
}