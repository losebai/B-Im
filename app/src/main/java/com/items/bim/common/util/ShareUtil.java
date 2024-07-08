package com.items.bim.common.util;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import androidx.core.content.FileProvider;

import java.io.File;

public class ShareUtil {


    // 原生通用分享文本
    public static void shareText(Activity activity, String title, String text) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.setType("text/plain");
        activity.startActivityForResult(Intent.createChooser(sendIntent, title), 80001);
    }

    // 原生通用分享图片
    public static void shareImage(Activity activity, String authority, String title, File file){
        shareImage(activity, authority, title, file, false);
    }
    public static void shareImage(Activity activity, String authority, String title, File file,  boolean isApp) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        Uri uri = getFileUri(activity, authority, file, isApp);
        sendIntent.putExtra(Intent.EXTRA_STREAM, uri);
        sendIntent.setType("image/png");
        activity.startActivityForResult(Intent.createChooser(sendIntent, title), 80002);
    }


    // 通用文件
    public static void shareFile(Activity activity, String authority, String type, File file,  boolean isApp) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        Uri uri = FileProvider.getUriForFile(activity, authority , file);
        sendIntent.putExtra(Intent.EXTRA_STREAM, uri);
        sendIntent.setType(type);
        activity.startActivityForResult(Intent.createChooser(sendIntent, file.getName()), 80002);
    }

    public static Uri getFileUri(Context context, String authority, File file, boolean isApp) {
        Uri uri;
        // 低版本直接用 Uri.fromFile
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            uri = Uri.fromFile(file);
        } else {
            if(isApp){
                // 分享当前应用下的共享路径中的
                uri = FileProvider.getUriForFile(context, authority , file);
            }else {
                // 分享外部的文件
                uri = getImageContentUri(context, file);
            }
        }
        return uri;
    }

    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID}, MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);
        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }
}
