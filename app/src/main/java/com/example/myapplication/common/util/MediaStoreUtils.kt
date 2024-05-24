package com.example.myapplication.common.util

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import java.io.File


data class MediaResource(
    val id: Long,
    val uri: Any,
    val displayName: String,
    val mimeType: String,
    val width: Int,
    val height: Int,
    val orientation: Int,
    val path: String,
    val size: Long,
    val bucketId: String?,
    val bucketDisplayName: String
)

object MediaStoreUtils {

    private val projection = arrayOf(
        MediaStore.Images.Media._ID,
        MediaStore.Images.Media.DISPLAY_NAME,
        MediaStore.Images.Media.MIME_TYPE,
        MediaStore.Images.Media.WIDTH,
        MediaStore.Images.Media.HEIGHT,
        MediaStore.Images.Media.SIZE,
        MediaStore.Images.Media.ORIENTATION,
        MediaStore.Images.Media.DATA,
        MediaStore.Images.Media.BUCKET_ID,
        MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
    )
    private const val imageSortOrder = "${MediaStore.Images.Media.DATE_MODIFIED} DESC"


    @SuppressLint("Range")
    suspend fun buildMediaResource(cursor: Cursor): MediaResource? {
        val data = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
        if (data.isBlank() || !File(data).exists()) {
            return null
        }
        val id = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media._ID))
        val displayName =
            cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME))
        val mimeType =
            cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.MIME_TYPE))
        val width = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media.WIDTH))
        val height =
            cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media.HEIGHT))
        val size = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.SIZE))
        val orientation =
            cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media.ORIENTATION))
        val bucketId =
            cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID))
        val bucketDisplayName =
            cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME))
        val contentUri = ContentUris.withAppendedId(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            id
        )
        return MediaResource(
            id = id,
            uri = contentUri,
            displayName = displayName,
            mimeType = mimeType,
            width = width,
            height = height,
            orientation = orientation,
            path = data,
            size = size,
            bucketId = bucketId,
            bucketDisplayName = bucketDisplayName,
        )
    }

    /**
     * @param [context]
     * @param [uri]
     * @return [List<MediaResource>]
     */
    suspend fun scanImage(
        context: Context,
        uri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        block: (MediaResource) -> Unit
    ) {
        val mediaCursor = context.contentResolver.query(
            uri,
            projection,
            null,
            null,
            imageSortOrder,
        )
        mediaCursor.use { cursor ->
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    buildMediaResource(cursor)?.let {
                        block(it)
                    }
                }
                cursor.close()
            }
        }
    }

    suspend fun get7DayImages(
        context: Context,
        uri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        block: (MediaResource) -> Unit
    ) {
        val mediaCursor = context.contentResolver.query(
            uri,
            projection,
            null,
            null,
            imageSortOrder,
        )
        mediaCursor.use { cursor ->
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    buildMediaResource(cursor)?.let {
                        block(it)
                    }
                    cursor.close()
                }
            }
        }
    }
}