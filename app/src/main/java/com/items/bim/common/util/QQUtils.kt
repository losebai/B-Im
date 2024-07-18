package com.items.bim.common.util

import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import com.items.bim.common.consts.SystemApp
import com.items.bim.common.provider.BaseContentProvider
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.GlobalScope

private val logger = KotlinLogging.logger {
}


object QQUtils {


    /****************
     *
     * 发起添加群流程。群号：BIM(966922403) 的 key 为： nHCirTItlbbWvY6z24XozWrO_N_7D6h6
     * 调用 joinQQGroup(nHCirTItlbbWvY6z24XozWrO_N_7D6h6) 即可发起手Q客户端申请加群 BIM(966922403)
     *
     * @param key 由官网生成的key
     * @return 返回true表示呼起手Q成功，返回false表示呼起失败
     */
    fun joinQQGroup(key: String) {
        val intent = Intent()
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http://qm.qq.com/cgi-bin/qm/qr?from=app&p=android&jump_from=webapi&k=$key"))
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面
        try {
            startActivity(BaseContentProvider.context(), intent, null)
        } catch (e: Exception) {
               logger.error { e }
            // 未安装手Q或安装的版本不支持
            Utils.message(GlobalScope, "未安装手Q或安装的版本不支持", SystemApp.snackBarHostState)
        }
    }
}