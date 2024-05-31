package com.example.myapplication.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.myapplication.common.util.ImageUtils
import com.example.myapplication.common.util.MediaStoreUtils
import com.example.myapplication.common.util.toFileEntity
import com.example.myapplication.dto.FileEntity
import java.util.Hashtable

private val EMPTY_IMAGES: Array<FileEntity>  = arrayOf()

class ImageViewModel() : ViewModel() {

    var groupName = "全部"

    var groupPath = ""

    val groupMap = Hashtable<String, Array<FileEntity>>()

    // 本机目录图片集合
    var dirList = mutableListOf<FileEntity>()


    fun loadPath(path: String){
        if (!groupMap.contains(path)){
            groupMap[path] = ImageUtils.getImageList(path).toTypedArray()
        }
    }

    fun reloadPath(path: String){
        groupMap[path] = ImageUtils.getImageList(path).toTypedArray()
    }

    fun reload(){
        dirList.clear()
    }

    fun getImageList(path: String) : Array<FileEntity> {
        groupMap[path]?.let {
            return it;
        }
        return EMPTY_IMAGES
    }

    fun getDay7Images(context: Context){
        val day7 = FileEntity()
        day7.name = "最近图片"
        day7.parentPath = "day7"
        day7.isDir = true
        dirList.add(0, day7)
        val day7List = ArrayList<FileEntity>()
        MediaStoreUtils.get7DayImages(context){ it, i ->
            day7List.add(it.toFileEntity().apply {
                this.index = i
            })
            day7.location = day7List[0].location
        }
        groupMap[day7.parentPath] = day7List.toTypedArray()
    }

    override fun onCleared() {
        super.onCleared()
        groupMap.clear()
    }
}