package com.items.bim.service

import com.items.bim.mc.dto.BannerDto

abstract class AbsToolService {

    abstract fun getBannerList() : List<BannerDto> ;


}