package com.example.myapplication.dto

import com.example.myapplication.mc.dto.MCAwardCart
import org.noear.snack.annotation.ONodeAttr

enum class LotteryPollEnum(@ONodeAttr val value: Int, val poolName: String) {
    INIT_ROLE(1, "角色新手"),
//    INIT_WEAPON(2, "武器新手"),
    UP_ROLE(5, "角色UP"),
    UP_WEAPON(6, "武器Up"),
    C_ROLE(7, "角色常驻"),
    C_WEAPON(8, "武器常驻");

    companion object {
        fun toUserStatus(int: Int): LotteryPollEnum {
            for (user: LotteryPollEnum in LotteryPollEnum.entries){
                if (user.value == int){
                    return user
                }
            }
            return LotteryPollEnum.INIT_ROLE
        }
    }
}

data class LotteryCount(
    val roleId: Long,
    val roleImageUri: String,
    val count: Int,
    val poolId: Int,
    val isUp: Boolean,
    val isOk: Boolean
)

data class LotteryPool(
    var poolId: Int = 0,
    var poolName: String = "",
    var poolBg: String = "",
    var poolImageUri: String = "",
    var poolType: LotteryPollEnum = LotteryPollEnum.INIT_ROLE,
    var group: String = "",
    var array: ArrayList<String> = ArrayList(),
    var maxCount: Int = 80,
    var probability4: Double = 0.0,
    var probability5: Double = 0.0,
    var startTime: String = "",
    var endTime: String = "",
    var awardCart: MCAwardCart = MCAwardCart()
)


data class Award(
    var id : Int = 0,
    var name: String = "",
    var star: Int = 0,
    var imageUri : String = "",
)