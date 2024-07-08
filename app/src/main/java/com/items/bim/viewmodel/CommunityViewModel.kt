package com.items.bim.viewmodel

import androidx.lifecycle.ViewModel
import com.items.bim.dto.CommunityEntity
import com.items.bim.entity.UserEntity
import com.items.bim.event.GlobalInitEvent
import com.items.bim.service.MessageDynamicService
import java.util.stream.Collectors

class CommunityViewModel: ViewModel() {

    private val communityList = ArrayList<CommunityEntity>()

    private val messageDynamicService: MessageDynamicService = MessageDynamicService()

    var page = 1

    private val size = 100

    private val pages = ArrayList<Int>()


    init {
        GlobalInitEvent.addUnit{
            this.nextCommunityPage()
        }
    }

    /**
     * 获取下一页动态
     * @return [List<CommunityEntity>]
     */
    fun nextCommunityPage() : List<CommunityEntity>{
        if (pages.contains(page)){
            return communityList
        }
        val appDynamicList = messageDynamicService.appDynamicPage(page, size)
        val communityList = appDynamicList.stream().map { item ->
            return@map CommunityEntity(UserEntity(item.userId, item.name, item.imageUrl, "",),
                "",item.dynamicBody, item.images, item.createTime)
        }.collect(Collectors.toList())
        this.communityList.addAll(communityList)
        pages.add(page++);
        return communityList
    }

    fun getCommunityList(): List<CommunityEntity> {
        return this.communityList
    }

    fun clearCommunityList(){
        page = 1
        this.pages.clear()
        this.communityList.clear()
    }
}