package com.items.bim.service

import com.items.bim.common.consts.AppAPI
import com.items.bim.common.util.HttpReqDto
import com.items.bim.common.util.HttpUtils
import okhttp3.Response
import org.noear.snack.ONode
import java.io.File

object FileService {


    fun uploadImage(path :String) : String{
        val file = File(path)
        if (!file.exists()){
            throw RuntimeException("$path 文件无效")
        }
        val res: Response? = HttpUtils.post(HttpReqDto.toBuild()
            .setUrl(AppAPI.FileAPI.POST_FILE_UPLOAD)
            .setFile(file)
            .build()
        )
        if (res?.isSuccessful == true) {
            val str = res.body?.string()
            val json = ONode.load(str)
            return json.toObject(String::class.java)
        }
        return ""
    }

    fun getImageUrl(path: String) : String{
        return HttpUtils.url("/dispatch-app/img/${path}", null)
    }
}