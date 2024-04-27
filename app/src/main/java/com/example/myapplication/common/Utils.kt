package com.example.myapplication.common

import android.content.res.Resources
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import com.example.myapplication.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

object Utils {

    fun message(scope: CoroutineScope, message: String ,snackbarHostState: SnackbarHostState){
        scope.launch {
            snackbarHostState.showSnackbar(
                message,
                actionLabel = "关闭",
                // Defaults to SnackbarDuration.Short
                duration = SnackbarDuration.Short
            )
        }
    }

}