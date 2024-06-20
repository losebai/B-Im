package com.example.myapplication.mc.dto

/**
 * 手册
 * @author 11450
 * @date 2024/06/12
 * @constructor 创建[Handbook]
 * @param [id] id
 * @param [name] 名字
 * @param [type] 类型
 * @param [desc] desc
 * @param [imageUri] 图像uri
 * @param [createTime] 创建时间
 */
data class Handbook(
    val id: Long,
    val name: String,
    val type: Int,
    val desc :String,
    val imageUri: String,
    val createTime: String?
)


/**
 * 书作用
 * @author 11450
 * @date 2024/06/12
 * @constructor 创建[RoleBook]
 * @param [id] id
 * @param [name] 名字
 * @param [imageUri] 图像uri
 * @param [star] 明星
 */
data class RoleBook(
    val id: Long,
    val name: String,
    val imageUri: String,
    val star: Int,
)