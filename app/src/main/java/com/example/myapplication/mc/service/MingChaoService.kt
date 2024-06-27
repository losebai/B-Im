package com.example.myapplication.mc.service

import com.example.myapplication.common.util.HttpReqDto
import com.example.myapplication.common.util.HttpUtils
import com.example.myapplication.mc.consts.MingChaoAPI
import com.example.myapplication.mc.dto.Announcement
import com.example.myapplication.mc.dto.Banner
import com.example.myapplication.mc.dto.BannerDto
import com.example.myapplication.mc.dto.RoleBook
import com.example.myapplication.mc.dto.CatalogueDto
import com.example.myapplication.mc.dto.HomeDto
import com.example.myapplication.service.AbsToolService
import okhttp3.Response
import org.noear.snack.ONode
import java.util.Collections

/**
 * 明超服务
 * @author 11450
 * @date 2024/06/12
 * @constructor 创建[MingChaoService]
 */
class MingChaoService : AbsToolService() {

    fun homePage() : HomeDto {
        val res: Response? = HttpUtils.post(
            HttpReqDto.Build().setUrl(MingChaoAPI.POST_HOME_PAGE)
                .addHeaders("Wiki_type", "9")
                .build()
        )
        if (res?.isSuccessful == true) {
            val json = ONode.load(res.body?.string())
            val contentJson = json["data"]["contentJson"]
            val announcement = contentJson["announcement"].toObjectList(Announcement::class.java)
            val banner = contentJson["banner"].toObjectList(Banner::class.java)
            return HomeDto(announcement, banner)
        }
        return HomeDto(listOf(), listOf())
    }

    override fun getBannerList() : List<BannerDto> {
        val res: Response? = HttpUtils.post(
            HttpReqDto.Build().setUrl(MingChaoAPI.POST_BANNER_LIST)
                .addBody("forumId", "0")
                .addBody("gameId", "3")
                .addHeaders("Source", "H5")
                .addHeaders("Version", "2.2.0")
                .build()
        )
        if (res?.isSuccessful == true) {
            val json = ONode.load(res.body?.string())
            return json["data"].toObjectList(BannerDto::class.java).apply {
                for(b : BannerDto in this){
                    val id = b.toAppIOS.substring(b.toAppIOS.indexOf("="), b.toAppIOS.lastIndex)
                    b.linkUri = "https://www.kurobbs.com/mc/post/${id}?enter_source=2"
                }
            }
        }
        return Collections.emptyList()
    }

    fun getRole(catalogueDto: CatalogueDto): MutableList<RoleBook> {
        return getCataloguePage(catalogueDto)
    }

    private fun getCataloguePage(catalogueDto: CatalogueDto): MutableList<RoleBook> {
        val res: Response? = HttpUtils.post(
            HttpReqDto.Build().setUrl(MingChaoAPI.POST_CATALOGUE).body(catalogueDto)
                .addHeaders("Wiki_type", "9")
                .build()
        )
        if (res?.isSuccessful == true) {
            val roles = ArrayList<RoleBook>()
            val json = ONode.load(res.body?.string())
            val records: ONode = json["data"]["results"]["records"]
            records.toObjectList(ONode::class.java).forEach {
                val context = it["content"]
                val star = context["star"].toObject<String>()
                roles.add(
                    RoleBook(
                        it["id"].toObject<Long>(), it["name"].toObject<String>(),
                        context["contentUrl"]?.toObject<String>() ?:  "",
                        if("normal" == star || star == null) 0 else star.toInt()
                    )
                )
            }
            return roles
        }
        return Collections.emptyList()
    }
}
