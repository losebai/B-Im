package com.items.bim.mc.dto

data class HomeDto(
    val announcement: List<Announcement>,
    val banner: List<Banner>
)


data class Banner(
    var describe: String = "",
    var linkConfig: LinkConfig = LinkConfig(),
    var url: String = "",
    var title: String = ""
)

data class LinkConfig(
    var linkType: Int = 0,
    var linkUrl: String = ""
)

data class Announcement(
    var content: String = "",
)
