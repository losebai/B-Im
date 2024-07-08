package com.items.bim.common

import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts

open class BaseActivityResultLauncher<I, O>(
    caller: ActivityResultCaller,
    contract: ActivityResultContract<I, O>
) {
    private var launcher: ActivityResultLauncher<I>
    private lateinit var callback: ActivityResultCallback<O>

    init {
        launcher = caller.registerForActivityResult(contract) { result ->
            callback.onActivityResult(result)
        }
    }

    open fun launch(
        input: I,
        callback: ActivityResultCallback<O>
    ) {
        this.callback = callback
        launcher.launch(input)
    }
}

class SinglePermissionLauncher(
    caller: ActivityResultCaller
): BaseActivityResultLauncher<String, Boolean>(
    caller,
    ActivityResultContracts.RequestPermission()
)

class MultiPermissionsLauncher(
    caller: ActivityResultCaller
): BaseActivityResultLauncher<Array<String>, Map<String, Boolean>>(
    caller,
    ActivityResultContracts.RequestMultiplePermissions()
)

class StartActivityForResultLauncher(
    caller: ActivityResultCaller
): BaseActivityResultLauncher<Intent, ActivityResult>(
    caller,
    ActivityResultContracts.StartActivityForResult()
)


