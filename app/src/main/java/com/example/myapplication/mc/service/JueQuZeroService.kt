package com.example.myapplication.mc.service

import com.example.myapplication.common.util.HttpReqDto
import com.example.myapplication.common.util.HttpUtils
import com.example.myapplication.mc.consts.JueQuZeroAPI
import com.example.myapplication.mc.dto.BannerDto
import com.example.myapplication.service.AbsToolService
import okhttp3.Response
import org.noear.snack.ONode
import java.util.Collections
import java.util.stream.Collectors

class JueQuZeroService(val jueQuZeroAPI: JueQuZeroAPI): AbsToolService() {

    override fun getBannerList() : List<BannerDto> {
        val res: Response? = HttpUtils.get(
            HttpReqDto.Build().setUrl(jueQuZeroAPI.GET_WEB_HOME)
                .addHeaders("X-Rpc-App_version", "2.71.0")
                .addHeaders("X-Rpc-Client_type", "4")
                .build()
        )
        if (res?.isSuccessful == true) {
            val json = ONode.load(res.body?.string())
            return json.get("data")["carousels"].toObjectList(ONode::class.java).stream().map {
                val banner = BannerDto()
                banner.url = it["cover"].toString()
                banner.linkUri = it["path"].toString()
                return@map banner;
            }.collect(Collectors.toList())
        }
        return Collections.emptyList()
    }
}