package com.items.bim.dto

data class UserLoginDto(val username: String,
                        val password: String,
                        var email:String? = null,
                        var code: String? = null)

