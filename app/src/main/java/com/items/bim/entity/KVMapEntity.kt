package com.items.bim.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "kv_map")
data class KVMapEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "first")
    val key: String,

    @ColumnInfo(name = "second")
    var value: String?

)
