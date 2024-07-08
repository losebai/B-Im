package com.items.bim.common

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat

class PermissionActivity : ComponentActivity() {

    private var singlePermissionLauncher: SinglePermissionLauncher = SinglePermissionLauncher(this)
    private var multiPermissionLauncher: MultiPermissionsLauncher = MultiPermissionsLauncher(this)
    private var startForResultLauncher: StartActivityForResultLauncher =
        StartActivityForResultLauncher(this)

    /**
     * 请求单个权限
     *
     * @param permission String
     * @param callback
     */
    fun requestSinglePermission(
        permission: String,
        callback: (isGranted: Boolean, shouldShowRequestPermissionRationale: Boolean) -> Unit
    ) {
        singlePermissionLauncher.launch(permission) {
            callback(it, ActivityCompat.shouldShowRequestPermissionRationale(this, permission))
        }
    }

    /**
     * 同时请求多个权限
     *
     * @param permissions Array<String>
     * @param callback
     */
    fun requestMultiPermissions(
        permissions: Array<String>,
        callback: (
            isAllGranted: Boolean,
            grantedList: List<String>,
            deniedList: List<String>,
            alwaysDeniedList: List<String>
        ) -> Unit
    ) {
        multiPermissionLauncher.launch(permissions) {
            val grantedList = it.filterValues { it }.mapNotNull { it.key }
            val leftoverList = (it - grantedList).map { it.key }
            val deniedList = leftoverList.filter {
                ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    it
                )
            }
            callback(
                grantedList.size == it.size, grantedList,
                deniedList, leftoverList - deniedList
            )
        }
    }

    /**
     * 代替 [AppCompatActivity.startActivityForResult]
     *
     * @param intent Intent
     * @param callback ActivityResultCallback<ActivityResult>
     */
    fun startActivityForResult(
        intent: Intent,
        callback: (requestCode: Int, data: Intent?) -> Unit
    ) {
        startForResultLauncher.launch(intent) {
            callback(it.resultCode, it.data)
        }
    }
}