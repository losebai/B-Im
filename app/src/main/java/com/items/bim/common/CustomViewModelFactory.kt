package com.items.bim.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class CustomViewModelFactory:  ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor().newInstance()
    }
}