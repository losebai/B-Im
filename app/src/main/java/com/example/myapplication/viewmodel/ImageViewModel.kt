package com.example.myapplication.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.myapplication.common.util.ImageUtils
import com.example.myapplication.entity.ImageEntity
import java.util.Hashtable

private val EMPTY_IMAGES: Array<ImageEntity>  = arrayOf()

class ImageViewModel : ViewModel() {

    var groupName = "全部"

    var groupPath = ""

    private val groupMap = Hashtable<String, Array<ImageEntity>>()

    // 分组集合 相机 qq 微信
    val groupList: List<ImageEntity> = mutableListOf()

    // 本机目录图片集合
    val dirList = ArrayList<ImageEntity>()

    // 是否加载
    var isLoad by mutableStateOf(false)

    // 详情
    var imageDetail by mutableStateOf(ImageEntity())


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