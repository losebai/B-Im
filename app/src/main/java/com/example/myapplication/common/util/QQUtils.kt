package com.example.myapplication.common.util

import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import com.example.myapplication.activity.LotteryActivity
import com.example.myapplication.common.consts.SystemApp
import com.example.myapplication.common.provider.BaseContentProvider
import kotlinx.coroutines.GlobalScope


object QQUtils {

    /****************
     *
     * 发起添加群流程。群号：xxxxxxxx 的 key 为： ydoaDyyAM5sk9VxPefuQJo-w6jf9pfK-
     * 调用 joinQQGroup(ydoaDyyAM5sk9VxPefuQJo-w6jf9pfK-) 即可发起手Q客户端申请加群 某某群(xxxxxxxx)
     *
     * @param key 由官网生成的key
     */
    fun joinQQGroup(key: String) {
        val intent = Intent()
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D$key"))
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面
        // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            startActivity(BaseContentProvider.context(), intent, null)
        } catch (e: Exception) {
            // 未安装手Q或安装的版本不支持
            Utils.message(GlobalScope, "未安装手Q或安装的版本不支持", SystemApp.snackBarHostState)
        }
    }
}