package com.example.myapplication.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.myapplication.common.provider.BaseContentProvider
import com.example.myapplication.common.util.ImageUtils
import com.example.myapplication.common.util.MediaStoreUtils
import com.example.myapplication.common.util.toFileEntity
import com.example.myapplication.dto.FileEntity
import com.example.myapplication.event.GlobalInitEvent
import java.util.Collections
import java.util.Hashtable

private val EMPTY_IMAGES: Array<FileEntity>  = arrayOf()

class ImageViewModel() : ViewModel() {

    var groupName = "全部"

    var groupPath = ""

    val groupMap = Hashtable<String, ArrayList<FileEntity>>()

    // 本机目录图片集合
    var dirList = mutableStateListOf<FileEntity>()


    init {
        groupMap[""] = arrayListOf()
        GlobalInitEvent.addUnit{
            getDay7Images(BaseContentProvider.context())
        }
    }


    fun loadPath(path: String){
        if (!groupMap.contains(path)){
            groupMap[path] = ImageUtils.getImageList(path)
        }
    }

    fun reloadPath(path: String){
        groupMap[path] = ImageUtils.getImageList(path)
    }

    fun reload(){
        dirList.clear()
    }

    fun getImageList(path: String) : List<FileEntity> {
        groupMap[path]?.let {
            return it;
        }
        return Collections.emptyList()
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
        groupMap[""]?.addAll(day7List)
        groupMap[day7.parentPath] = day7List
    }

    override fun onCleared() {
        super.onCleared()
        groupMap.clear()
    }
}