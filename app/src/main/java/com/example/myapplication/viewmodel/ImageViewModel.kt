package com.example.myapplication.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.myapplication.common.util.ImageUtils
import com.example.myapplication.entity.FileEntity
import java.util.Hashtable

private val EMPTY_IMAGES: Array<FileEntity>  = arrayOf()

class ImageViewModel() : ViewModel() {

    var groupName = "全部"

    var groupPath = ""

    private val groupMap = Hashtable<String, Array<FileEntity>>()

    // 分组集合 相机 qq 微信
    val groupList: List<FileEntity> = mutableListOf()

    // 本机目录图片集合
    val dirList = ArrayList<FileEntity>()

    // 是否加载
    var isLoad by mutableStateOf(false)

    // 详情
    var imageDetail = FileEntity()

    init {
        val img = FileEntity()
        img.name = "最近照片"
        dirList.add(img)
    }





    fun loadPath(path: String){
        if (!groupMap.contains(path)){
            groupMap[path] = ImageUtils.getImageList(path).toTypedArray()
        }
    }

    fun reloadPath(path: String){
        groupMap[path] = ImageUtils.getImageList(path).toTypedArray()
    }

    fun getImageList(path: String) : Array<FileEntity> {
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