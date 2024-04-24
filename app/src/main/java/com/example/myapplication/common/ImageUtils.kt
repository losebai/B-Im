package com.example.myapplication.common

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.example.myapplication.entity.ImageEntity
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import java.io.File
import java.io.FilenameFilter

object ImageUtils {

    private val FILETYPE = listOf(".png", ".jpg", ".gif")

    private val filenameFilter: (File, String) -> Boolean =
        { dir: File, name: String -> checkFix(name) }

    val imagePathsList = ArrayList<String>()

    val imageDirList = ArrayList<File>()

    val emptyImage: ImageEntity = ImageEntity(null, null, null, false)

    // 获取存储目录路径
    val storageDirPath: String = Environment.getExternalStorageDirectory().absolutePath

    // 获取相机目录路径
    val cameraDirPath: String =
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).absolutePath

    // 获取相册目录路径
    val galleryDirPath: String =
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
                for (file in it) {
                    if (file.isDirectory) {
                        val oneImageEntity = getImageDirectoryOne(file)
                        if (oneImageEntity != null){
                            oneImageEntity.isDir = true
                            oneImageEntity.name = file.name
                            fileList.add(oneImageEntity)
                        }
                    }
                }
            }
        }
        return fileList
    }

    private fun getImageDirectoryOne(dir: File): ImageEntity? {
        val files = dir.listFiles(filenameFilter)
        if (files != null && files.isNotEmpty()) {
            return ImageEntity(files[0])
        }
        return null
    }

    fun getImageList(path: String): List<ImageEntity> {
        val dir = File(path)
        val fileList = ArrayList<ImageEntity>()
        if (dir.isDirectory) {
            val files = dir.listFiles(filenameFilter)
            files?.let {
                for (file in it) {
                    // 获取图片文件路径
                    fileList.add(ImageEntity(file))
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


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @Composable
    fun CheckPermission() {
        PermissionUtils.ApplyPermission(Manifest.permission.READ_MEDIA_IMAGES) {laun->
            SideEffect {
                laun?.launch(Manifest.permission.READ_MEDIA_IMAGES)
            }
        }
    }
}