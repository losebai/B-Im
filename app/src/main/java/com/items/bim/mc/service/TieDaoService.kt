package com.items.bim.mc.service

import com.items.bim.common.util.HttpReqDto
import com.items.bim.common.util.HttpUtils
import com.items.bim.mc.consts.TieDaoAPI
import com.items.bim.mc.dto.BannerDto
import com.items.bim.service.AbsToolService
import okhttp3.Response
import org.noear.snack.ONode
import java.util.Collections
import java.util.stream.Collectors

class TieDaoService(val tieDaoAPI: TieDaoAPI) : AbsToolService() {

    override fun getBannerList() : List<BannerDto> {
        val res: Response? = HttpUtils.get(
            HttpReqDto.Build().setUrl(tieDaoAPI.GET_WEB_HOME)
                .addHeaders("X-Rpc-App_version", "2.71.0")
                .addHeaders("X-Rpc-Client_type", "4")
                .build()
        )
        if (res?.isSuccessful == true) {
            val json = ONode.load(res.body?.string())
            return json.get("data")["carousels"].toObjectList(ONode::class.java).stream().map {
                val banner = BannerDto()
                banner.url = it["cover"].toObject(String::class.java)
                banner.linkUri = it["path"].toObject(String::class.java)
                return@map banner;
            }.collect(Collectors.toList())
        }
        return Collections.emptyList()
    }
}