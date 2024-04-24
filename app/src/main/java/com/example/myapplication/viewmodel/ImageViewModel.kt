package com.example.myapplication.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.myapplication.entity.ImageEntity

class ImageViewModel : ViewModel() {

    var groupName by mutableStateOf("全部")

    var groupPath by mutableStateOf("")

    val list = ArrayList<ImageEntity>()

    val groupList = ArrayList<ImageEntity>()

    private val groupNames = ArrayList<String>()

    var isInit by mutableStateOf(false)

    private fun groupAddAll(images: ArrayList<ImageEntity>){
        for ( c:ImageEntity in  images){
            if (c.isDir){
                groupNames.add(c.name)
                groupList.add(c)
            }else{
                list.add(c)
            }
        }
    }

    fun onGroupPathChanged(newString:String){
        groupPath = newString
    }

    override fun onCleared() {
        super.onCleared()
    }
}