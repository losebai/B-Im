package com.items.bim.mc.dto

data class CatalogueDto(
    val catalogueId: Int,
    val page: Int = 1,
    val limit: Int = 1000
)