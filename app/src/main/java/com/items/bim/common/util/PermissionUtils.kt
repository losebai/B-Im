package com.items.bim.common.util

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.core.content.ContextCompat


object PermissionUtils  {

    private val permissionList: ArrayList<String> = ArrayList<String>();

    private var singlePermissionResultLauncher: ManagedActivityResultLauncher<String, Boolean>? =
        null

    private var MultiplePermissionResultLauncher: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>? =
        null

    @Composable
    fun ApplyPermission(
        permission: String,
        context: @Composable (ManagedActivityResultLauncher<String, Boolean>?) -> Unit
    ) {
        val singlePermissionResultLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { isGranted ->
                if (isGranted) {
                    permissionList.add(permission)
                }
            }
        )
        context(singlePermissionResultLauncher)
    }

    @Composable
    fun ApplyPermission(
        permission: Array<String>,
        context: @Composable (ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>?) -> Unit
    ) {
        MultiplePermissionResultLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions(),
            onResult = { isGranted ->
                isGranted.forEach { (t, u) ->
                    if (u) {
                        permissionList.add(t)
                    }
                }
            }
        )
        context(MultiplePermissionResultLauncher)
    }


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @Composable
    fun CheckPermission(permission: String) {
        if (permissionList.contains(permission)) {
            return
        }
        ApplyPermission(permission) { laun ->
            SideEffect {
                laun?.launch(permission)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @Composable
    fun CheckPermission(permissions: Array<String>) {
        ApplyPermission(permissions) { laun ->
            SideEffect {
                laun?.launch(permissions)
            }
        }
    }

}


class PermissionsChecker(context: Context) {

    private var mContext: Context = context.applicationContext

    // 判断权限集合
    fun lacksPermissions(vararg permissions: String): Boolean {
        for (permission in permissions) {
            if (lacksPermission(permission)) {
                return true
            }
        }
        return false
    }

    // 判断是否缺少权限
    fun lacksPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(mContext, permission) == PackageManager.PERMISSION_DENIED
    }
}



