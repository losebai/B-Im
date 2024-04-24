package com.example.myapplication.common

import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable


object PermissionUtils {

    private val permissionList: ArrayList<String> = ArrayList<String>();

    private var singlePermissionResultLauncher: ManagedActivityResultLauncher<String, Boolean>? =
        null

    private var MultiplePermissionResultLauncher: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>? =
        null

    @Composable
    fun ApplyPermission(
        permission: String,
        context:  @Composable (ManagedActivityResultLauncher<String, Boolean>?) -> Unit
    ) {
        if (singlePermissionResultLauncher == null) {
            singlePermissionResultLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission(),
                onResult = { isGranted ->
                }
            )
        }
        permissionList.add(permission)
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
            }
        )
        context(MultiplePermissionResultLauncher)
    }

}



