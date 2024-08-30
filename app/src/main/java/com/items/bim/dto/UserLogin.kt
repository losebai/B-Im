package com.items.bim.dto

data class UserLoginDto(val username: String,
                        val password: String,
                        val name: String = "游客1",
                        var email:String? = null,
                        var code: String? = null)

