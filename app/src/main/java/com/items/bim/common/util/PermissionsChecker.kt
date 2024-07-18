package com.items.bim.common.util

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.items.bim.common.provider.BaseContentProvider


class PermissionsChecker {


    companion object {

        fun getInstance() = PermissionsChecker()

    }

    private var mContext: Context = BaseContentProvider.context()


    // 判断权限集合
    fun lacksPermissions(permissions: Array<String>): Boolean {
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


