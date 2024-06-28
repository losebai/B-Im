package com.example.myapplication.dto

data class UserGameDto(
    var name: String = "",
    var imageUri : String = "",
    var uid: Long = 0L,
    var tag: String = "",
    var zonRaking: Int= 0
)

data class RakingDto(
    var name: String = "",
    var imageUri : String = "",
    var uid: Long = 0L,
    var raking : Int = 1
)