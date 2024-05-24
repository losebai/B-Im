//package com.example.myapplication.ui
//
//import android.content.ContentUris
//import android.content.Context
//import android.provider.MediaStore
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.withContext
//import java.io.File
//
//
//fun getImages(context : Context) :ArrayList<MediaResource>?{
//    val projection = arrayOf(
//        MediaStore.Images.Media._ID,
//        MediaStore.Images.Media.DISPLAY_NAME,
//        MediaStore.Images.Media.MIME_TYPE,
//        MediaStore.Images.Media.WIDTH,
//        MediaStore.Images.Media.HEIGHT,
//        MediaStore.Images.Media.SIZE,
//        MediaStore.Images.Media.ORIENTATION,
//        MediaStore.Images.Media.DATA,
//        MediaStore.Images.Media.BUCKET_ID,
//        MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
//    )
//    val sortOrder = "${MediaStore.Images.Media.DATE_MODIFIED} DESC"
//    val mediaResourceList = mutableListOf<MediaResource>()
//    try {
//        val mediaCursor = context.contentResolver.query(
//            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//            projection,
//            null,
//            null,
//            sortOrder,
//        ) ?: return null
//        mediaCursor.use { cursor ->
//            while (cursor.moveToNext()) {
//                val data = cursor.getString(MediaStore.Images.Media.DATA)
//                if (data.isBlank() || !File(data).exists()) {
//                    continue
//                }
//                val id = cursor.getLong(MediaStore.Images.Media._ID)
//                val displayName = cursor.getString(MediaStore.Images.Media.DISPLAY_NAME)
//                val mimeType = cursor.getString(MediaStore.Images.Media.MIME_TYPE)
//                val width = cursor.getInt(MediaStore.Images.Media.WIDTH)
//                val height = cursor.getInt(MediaStore.Images.Media.HEIGHT)
//                val size = cursor.getLong(MediaStore.Images.Media.SIZE)
//                val orientation = cursor.getInt(MediaStore.Images.Media.ORIENTATION)
//                val bucketId = cursor.getString(MediaStore.Images.Media.BUCKET_ID)
//                val bucketDisplayName =
//                    cursor.getString(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
//                val contentUri = ContentUris.withAppendedId(
//                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                    id
//                )
//                val mediaResource = MediaResource(
//                    id = id,
//                    uri = contentUri,
//                    displayName = displayName,
//                    mimeType = mimeType,
//                    width = width,
//                    height = height,
//                    orientation = orientation,
//                    path = data,
//                    size = size,
//                    bucketId = bucketId,
//                    bucketDisplayName = bucketDisplayName,
//                )
//                mediaResourceList.add(mediaResource)
//            }
//        }
//    } catch (e: Throwable) {
//        e.printStackTrace()
//    }
//    return mediaResourceList
//
//
//}
//
//class MediaResource(
//    id: Long,
//    uri: Any,
//    displayName: String?,
//    mimeType: String?,
//    width: Int,
//    height: Int,
//    orientation: Int,
//    path: String?,
//    size: Long,
//    bucketId: String?,
//    bucketDisplayName: String?
//)
//
