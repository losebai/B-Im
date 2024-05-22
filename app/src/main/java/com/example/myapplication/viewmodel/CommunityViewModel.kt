package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import com.example.myapplication.entity.AppDynamic
import com.example.myapplication.entity.CommunityEntity
import com.example.myapplication.entity.UserEntity
import com.example.myapplication.service.MessageService
import java.util.stream.Collectors

class CommunityViewModel: ViewModel() {

    private val communityList = ArrayList<CommunityEntity>()

    private val messageService: MessageService = MessageService()

    var page = 1

    private val size = 10

    private val pages = ArrayList<Int>()

    /**
     * 获取下一页动态
     * @return [List<CommunityEntity>]
     */
    fun nextCommunityPage() : List<CommunityEntity>{
        if (pages.contains(page)){
            return communityList
        }
        val appDynamicList = messageService.appDynamicPage(page, size)
        val communityList = appDynamicList.stream().map { item ->
            return@map CommunityEntity(UserEntity(item.userId, item.name, item.imageUrl, "",),
                item.dynamicBody, item.images, item.createTime)
        }.collect(Collectors.toList())
        this.communityList.addAll(communityList)
        pages.add(page++);
        return communityList
    }

    fun getCommunityList(): List<CommunityEntity> {
        return this.communityList
    }

    fun clearCommunityList(){
        this.pages.clear()
        this.communityList.clear()
    }
}