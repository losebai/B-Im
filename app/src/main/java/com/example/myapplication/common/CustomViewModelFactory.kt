package com.example.myapplication.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class CustomViewModelFactory<T>:  ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor().newInstance()
    }
}