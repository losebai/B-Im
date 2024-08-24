package com.items.bim.common.util

import android.util.Log
import com.items.bim.common.consts.SystemApp
import kotlinx.coroutines.MainScope
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.noear.snack.ONode
import java.io.IOException


class HttpCallback(val onHandler: (ONode) -> Unit,val onError : () -> Unit): Callback{

    var msgField = "description"

    override fun onFailure(call: Call, e: IOException) {
        e.message?.let {
            Log.e("api", it)
            Utils.message(MainScope(), it, SystemApp.snackBarHostState)
        }
    }

    override fun onResponse(call: Call, response: Response) {
        if (!response.isSuccessful){
            Log.e("api","未知异常 ${response.code}")
            this.onError()
//            Utils.message(MainScope(), "未知异常 ${response.code}", SystemApp.snackBarHostState)
            return
        }
        val json = ONode.load(response.body?.string())
        if (!json.get("code").toObject<Int>().equals(200)){
            Log.e("api", json.toJson())
            this.onError()
//            Utils.message(MainScope(), json.get(msgField).toObject<String>(), SystemApp.snackBarHostState)
            return
        }
        val data = json.getOrNull("data")
        if (data == null){
            this.onError()
            return
        }
        onHandler(data)
    }
}