package com.items.bim.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class PermissionViewModel : ViewModel() {


    var isCheck by mutableStateOf(false)


    var photoPermission by mutableStateOf(false)

    var appPermission by mutableStateOf(false)

    var networkPermission by mutableStateOf(false)

    var filePermission by mutableStateOf(false)


}