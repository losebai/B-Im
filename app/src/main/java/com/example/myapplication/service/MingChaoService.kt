package com.example.myapplication.service

import com.example.myapplication.common.consts.AppAPI
import com.example.myapplication.common.util.HttpUtils
import com.example.myapplication.dto.RoleBook
import com.example.myapplication.dto.mingchao.CatalogueDto
import com.example.myapplication.remote.entity.AppUserEntity
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.noear.snack.ONode
import java.util.Collections

class MingChaoService {


    fun getRole() : MutableList<RoleBook> {
        return getCataloguePage(CatalogueDto(AppAPI.MingChao.ROLE))
    }


    private fun getCataloguePage(catalogueDto: CatalogueDto): MutableList<RoleBook> {
        val requestBody: RequestBody = ONode.serialize(catalogueDto).toRequestBody(HttpUtils.MEDIA_TYPE_JSON)
        val res: Response? = HttpUtils.post(AppAPI.MingChao.POST_CATALOGUE, requestBody)
        if (res?.isSuccessful == true){
            val roles = ArrayList<RoleBook>()
            val json = ONode.load(res.body?.string())
            val records: ONode = json["data"]["results"]["records"]
            records.toObjectList(ONode::class.java).forEach {
                val context = it["context"]
                roles.add(RoleBook(it["id"].toObject(), it["name"].toObject(),
                    context["contentUrl"].toObject(), it["star"].toObject()))
            }
            return roles
        }
        return Collections.emptyList()
    }
}
