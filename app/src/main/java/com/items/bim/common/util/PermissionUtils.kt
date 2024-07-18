package com.items.bim.common.util

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.room.util.TableInfo
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.items.bim.common.provider.BaseContentProvider


object PermissionUtils  {

     val permissionList: ArrayList<String> = ArrayList<String>();

     var singlePermissionResultLauncher: ManagedActivityResultLauncher<String, Boolean>? =
        null

     var MultiplePermissionResultLauncher: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>? =
        null
}



@Composable
fun ApplyPermission(
    permission: String,
    context: @Composable (ManagedActivityResultLauncher<String, Boolean>?) -> Unit
) {
    val singlePermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                PermissionUtils.permissionList.add(permission)
            }
        }
    )

    // 基于 LocalLifecycleOwner 获取 Lifecycle
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    // 在 Activity onStart 时，发起权限事情，如果权限已经获得则跳过
    val lifecycleObserver = remember {
        LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                if (PermissionsChecker.getInstance().lacksPermission(permission)) {
                    singlePermissionResultLauncher.launch(permission)
                }
            }
        }
    }

    // 当 Lifecycle 或者 LifecycleObserver 变化时注册回调，注意 onDispose 中的注销处理避免泄露
    DisposableEffect(lifecycle, lifecycleObserver) {
        lifecycle.addObserver(lifecycleObserver)
        onDispose {
            lifecycle.removeObserver(lifecycleObserver)
        }
    }
    context(singlePermissionResultLauncher)
}

@Composable
fun ApplyPermission(  permissions: Array<String> ,context: @Composable (ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>?) -> Unit
) {
    val multiplePermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { isGranted ->
            isGranted.forEach { (t, u) ->
                if (u) {
                    PermissionUtils.permissionList.add(t)
                }
            }
        }
    )
    // 基于 LocalLifecycleOwner 获取 Lifecycle
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    // 在 Activity onStart 时，发起权限事情，如果权限已经获得则跳过
    val lifecycleObserver = remember {
        LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                if (PermissionsChecker.getInstance().lacksPermissions(permissions)) {
                    multiplePermissionResultLauncher.launch(permissions)
                }
            }
        }
    }

    // 当 Lifecycle 或者 LifecycleObserver 变化时注册回调，注意 onDispose 中的注销处理避免泄露
    DisposableEffect(lifecycle, lifecycleObserver) {
        lifecycle.addObserver(lifecycleObserver)
        onDispose {
            lifecycle.removeObserver(lifecycleObserver)
        }
    }
    context(PermissionUtils.MultiplePermissionResultLauncher)
}


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun CheckPermission(permission: String) {
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

@SuppressLint("PermissionLaunchedDuringComposition")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SinglePermission(permission: String) {
    val permissionState =
        rememberPermissionState(permission = permission)
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(key1 = lifecycleOwner, effect = {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    permissionState.launchPermissionRequest()
                }
                Lifecycle.Event.ON_CREATE -> {

                }
                Lifecycle.Event.ON_RESUME -> {}
                Lifecycle.Event.ON_PAUSE -> {}
                Lifecycle.Event.ON_STOP -> {}
                Lifecycle.Event.ON_DESTROY -> {}
                Lifecycle.Event.ON_ANY -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    })

    when {
        permissionState.status.isGranted -> {
            Text(text = "Reading external permission is granted")
        }
        permissionState.status.shouldShowRationale -> {
            Column {
                Text(text = "Reading external permission is required by this app")
            }
        }
        !permissionState.status.isGranted && !permissionState.status.shouldShowRationale -> {
            Text(text = "Permission fully denied. Go to settings to enable")
        }
    }
}


@ExperimentalPermissionsApi
@Composable
fun MultiplePermissions(permissions: List<String>) {
    val permissionStates = rememberMultiplePermissionsState(
        permissions = permissions
    )
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(key1 = lifecycleOwner, effect = {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    permissionStates.launchMultiplePermissionRequest()
                }

                Lifecycle.Event.ON_CREATE -> {}
                Lifecycle.Event.ON_RESUME ->  {}
                Lifecycle.Event.ON_PAUSE ->  {}
                Lifecycle.Event.ON_STOP ->  {}
                Lifecycle.Event.ON_DESTROY ->  {}
                Lifecycle.Event.ON_ANY -> {
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    })
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    )
    {
        permissionStates.permissions.forEach { it ->
            when (it.permission) {
                Manifest.permission.READ_EXTERNAL_STORAGE -> {
                    when {
                        it.status.isGranted -> {
                            /* Permission has been granted by the user.
                               You can use this permission to now acquire the location of the device.
                               You can perform some other tasks here.
                            */
                            Text(text = "Read Ext Storage permission has been granted")
                        }
                        it.status.shouldShowRationale -> {
                            /*Happens if a user denies the permission two times

                             */
                            Text(text = "Read Ext Storage permission is needed")
                        }
                        !it.status.isGranted && !it.status.shouldShowRationale -> {
                            /* If the permission is denied and the should not show rationale
                                You can only allow the permission manually through app settings
                             */
                            Text(text = "Navigate to settings and enable the Storage permission")

                        }
                    }
                }
                Manifest.permission.ACCESS_FINE_LOCATION -> {
                    when {
                        it.status.isGranted -> {
                            /* Permission has been granted by the user.
                               You can use this permission to now acquire the location of the device.
                               You can perform some other tasks here.
                            */
                            Text(text = "Location permission has been granted")
                        }
                        it.status.shouldShowRationale -> {
                            /*Happens if a user denies the permission two times

                             */
                            Text(text = "Location permission is needed")

                        }
                        !it.status.isGranted && !it.status.shouldShowRationale -> {
                            /* If the permission is denied and the should not show rationale
                                You can only allow the permission manually through app settings
                             */
                            Text(text = "Navigate to settings and enable the Location permission")

                        }
                    }
                }
            }
        }
    }
}



