package com.example.myapplication.service

import com.example.myapplication.mc.dto.BannerDto

abstract class AbsToolService {

    abstract fun getBannerList() : List<BannerDto> ;


}