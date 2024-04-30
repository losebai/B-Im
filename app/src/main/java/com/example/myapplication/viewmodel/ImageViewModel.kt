package com.example.myapplication.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.myapplication.common.ImageUtils
import com.example.myapplication.entity.ImageEntity
import java.util.Hashtable

private val EMPTY_IMAGES: Array<ImageEntity>  = arrayOf()

class ImageViewModel : ViewModel() {

    var groupName by mutableStateOf("全部")

    var groupPath by mutableStateOf("")

    private val groupMap = Hashtable<String, Array<ImageEntity>>()

    // 目录图片集合
    val groupList = ArrayList<ImageEntity>()

    // 是否加载
    var isLoad by mutableStateOf(false)

    // 详情
    var imageDetail by mutableStateOf(ImageEntity())

    var imageDetailIndex = 0

    fun loadPath(path: String){
        if (!groupMap.contains(path)){
            groupMap[path] = ImageUtils.getImageList(path).toTypedArray()
        }
    }

    fun reloadPath(path: String){
        groupMap[path] = ImageUtils.getImageList(path).toTypedArray()
    }

    fun getImageList(path: String) : Array<ImageEntity> {
        groupMap[path]?.let {
            return it;
        }
        return EMPTY_IMAGES
    }

    override fun onCleared() {
        super.onCleared()
        groupMap.clear()
    }
}