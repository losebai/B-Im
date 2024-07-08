package com.items.bim.dto

import android.os.Parcelable
import com.example.miaow.base.http.HttpResponse
import kotlinx.parcelize.Parcelize


@Parcelize
data class TreeList(
    val data: List<Tree>? = null,
) : HttpResponse(), Parcelable

@Parcelize
data class Tree(
    val children: List<Tree>? = null,
    var childrenSelectPosition: Int = 0,
    val courseId: String = "",
    val id: String = "",
    val name: String = "",
    val order: String = "",
    val parentChapterId: String = "",
    val userControlSetTop: String = "",
    val visible: String = ""
) : Parcelable
