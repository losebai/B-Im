package com.example.myapplication.service

import com.example.myapplication.common.AppAPI
import com.example.myapplication.common.HttpUtils
import okhttp3.Response

class UserService {


    fun getUser(id: Int){
        val res: Response = HttpUtils.get(AppAPI.GET_USER)
        if (res.isSuccessful){
            val json = ONode
//            return res.body.
        }
    }
}