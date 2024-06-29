package com.example.myapplication.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "mc_record")
data class McRecordEntity(
    @PrimaryKey(autoGenerate=true)
    var id: Long = 0,
    var cardPoolType: String = "",
    var resourceId: Int = 0,
    var qualityLevel: Int = 0,
    var resourceType: String = "",
    var name: String = "",
    var count: Int = 0,
    var time: String = "",
)