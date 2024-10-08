package com.items.bim.dto

import java.io.File

/**
 * 本地图片实体
 */
class FileEntity {
    var name: String = ""
    var location: String = ""
    var isDir = false
    var index = 0
    var filePath: String = ""
    var parentPath: String = ""
    var dirSize = 0

    constructor()
    constructor(file: File) {
        name = file.getName()
        location = file.toURI().toString()
        isDir = file.isDirectory()
        filePath = file.path
        parentPath = file.getParent() ?: ""
    }

    constructor(file: File, index: Int) {
        name = file.getName()
        location = file.toURI().toString()
        isDir = file.isDirectory()
        filePath = file.path
        parentPath = file.getParent() ?: ""
        this.index = index
    }

}
