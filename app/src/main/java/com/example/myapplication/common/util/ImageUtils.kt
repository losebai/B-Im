package com.example.myapplication.common.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Environment
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.myapplication.entity.ImageEntity
import java.io.File

object ImageUtils {

    private val FILETYPE = listOf(".png", ".jpg", ".gif")

    private val filenameFilter: (File, String) -> Boolean =
        { dir: File, name: String -> checkFix(name) }

    private val imagePathsList = ArrayList<String>()

    private val imageDirList = ArrayList<File>()

    // 获取相册目录路径
    val DCIM_PATH: String =
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).absolutePath

    // 获取照片目录路径
    val PICTURES_PATH: String =
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).absolutePath


    /**
     * 获取一级文件夹
     * @param path 路径
     * @return list
     */
    fun getDirectoryList(path: String): List<ImageEntity> {
        val dir = File(path)
        val fileList = ArrayList<ImageEntity>()
        if (dir.isDirectory) {
            // 筛选出文件夹
            val files = dir.listFiles { _dir: File, p: String ->
                _dir.isDirectory() && "." != p.substring(
                    0,
                    1
                )
            }
            files?.let {
                var index = 0
                for (file in it) {
                    if (file.isDirectory) {
                        val oneImageEntity = getImageDirectoryOne(file)
                        if (oneImageEntity != null){
                            oneImageEntity.name = file.name
                            oneImageEntity.index = index++
                            fileList.add(oneImageEntity)
                        }
                    }
                }
            }
        }
        return fileList
    }

    fun getImageDirectoryOne(dir: File): ImageEntity? {
        val files = dir.listFiles(filenameFilter)
        if (files != null && files.isNotEmpty()) {
            return ImageEntity(files[files.size - 1]).let {
                it.isDir = true
                it.dirSize = files.size
                it
            }
        }
        return null
    }

    fun getImageList(path: String): List<ImageEntity> {
        val dir = File(path)
        val fileList = ArrayList<ImageEntity>()
        if (dir.isDirectory) {
            val files = dir.listFiles(filenameFilter)
            var index = 0
            files?.let {
                for (file in it) {
                    // 获取图片文件路径
                    fileList.add(ImageEntity(file, index++))
                }
            }
        }
        return fileList
    }

    /**
     * 递归遍历图片文件
     * @param dir dir
     */
    private fun traverseImageDirFiles(dir: File) {
        if (dir.isDirectory) {
            val files = dir.listFiles()
            files?.let {
                for (file in it) {
                    if (file.isDirectory) {
                        imageDirList.add(file)
                        traverseImageDirFiles(file)
                    }
                }
            }
        }
    }

    /**
     * 递归遍历图片文件
     * @param dir dir
     */
    private fun traverseImageFiles(dir: File) {
        if (dir.isDirectory) {
            val files = dir.listFiles()
            files?.let {
                for (file in it) {
                    if (file.isDirectory) {
                        traverseImageFiles(file)
                    } else {
                        // 处理图片文件
                        processImageFile(file)
                    }
                }
            }
        }
    }

    private fun checkFix(name: String): Boolean {
        val fixd = name.lastIndexOf(".")
        if (fixd == -1) {
            return false
        }
        val fix = name.substring(fixd)
        return FILETYPE.contains(fix)
    }

    // 处理图片文件
    private fun processImageFile(file: File) {
        val name = file.name
        if (checkFix(name)) {
            // 获取图片文件路径
            val imagePath = file.toURI()
            imagePathsList.add(imagePath.toString())
        }
    }

    fun check(context: Context, activity: Activity) {
        // 检查是否有存储权限
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // 请求存储权限
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                1
            )
        }

        // 检查是否有相机权限
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // 请求相机权限
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.CAMERA), 1)
        }

        // 检查是否有读取联系人权限
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // 请求读取联系人权限
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.READ_CONTACTS),
                1
            )
        }
    }
}