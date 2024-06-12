package com.example.myapplication.service

import com.example.myapplication.common.consts.AppAPI
import com.example.myapplication.common.util.HttpReqDto
import com.example.myapplication.common.util.HttpUtils
import com.example.myapplication.dto.RoleBook
import com.example.myapplication.dto.mingchao.CatalogueDto
import okhttp3.Response
import org.noear.snack.ONode
import java.util.Collections

/**
 * 明超服务
 * @author 11450
 * @date 2024/06/12
 * @constructor 创建[MingChaoService]
 */
class MingChaoService {

    fun getRole(catalogueDto: CatalogueDto): MutableList<RoleBook> {
        return getCataloguePage(catalogueDto)
    }

    private fun getCataloguePage(catalogueDto: CatalogueDto): MutableList<RoleBook> {
        val res: Response? = HttpUtils.post(
            HttpReqDto.Build().setUrl(AppAPI.MingChao.POST_CATALOGUE).body(catalogueDto)
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
