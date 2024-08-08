package com.items.bim.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.items.bim.common.provider.BaseContentProvider
import com.items.bim.common.util.ImageUtils
import com.items.bim.common.util.MediaStoreUtils
import com.items.bim.common.util.toFileEntity
import com.items.bim.dto.FileEntity
import com.items.bim.event.GlobalInitEvent
import java.util.Collections
import java.util.Hashtable


class ImageViewModel() : ViewModel() {

    var groupName = "全部"

    var groupPath = ""

    val groupMap = Hashtable<String, ArrayList<FileEntity>>()

    // 本机目录图片集合
    var dirList = ArrayList<FileEntity>()

    var imageDetail = mutableStateOf(FileEntity())

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
        dirList.subList(0, dirList.size - 1).clear()
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
        day7.parentPath = ""
        day7.isDir = true
        dirList.add(0, day7)
        val day7List = ArrayList<FileEntity>()
        MediaStoreUtils.get7DayImages(context){ it, i ->
            day7List.add(it.toFileEntity().apply {
                this.index = i
            })
            day7.location = day7List[0].location
            day7.dirSize = day7List.size
        }
        groupMap[""]?.addAll(day7List)
    }

    override fun onCleared() {
        super.onCleared()
        groupMap.clear()
    }
}