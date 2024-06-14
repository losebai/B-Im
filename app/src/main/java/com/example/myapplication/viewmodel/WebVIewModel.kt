package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.common.util.WanHelper
import com.example.myapplication.dto.HotKey
import com.example.myapplication.dto.Tree
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class WanUiState(
    var hotKeyResult: MutableList<HotKey> = ArrayList(),
    var treeResult: MutableList<Tree> = ArrayList(),
    var searchHistoryResult: MutableList<String> = ArrayList(),
    var webBookmarkResult: MutableList<String> = ArrayList(),
    var webHistoryResult: MutableList<String> = ArrayList(),
    var updateTime: Long = 0
)

class WebVIewModel() : ViewModel(){

    private val _uiState = MutableStateFlow(WanUiState())

    val uiState: StateFlow<WanUiState> = _uiState.asStateFlow()



    fun onSearchHistory(isAdd: Boolean, text: String) {
        viewModelScope.launch {
            _uiState.update {
                if (it.searchHistoryResult.contains(text)) {
                    it.searchHistoryResult.remove(text)
                }
                if (isAdd) {
                    it.searchHistoryResult.add(0, text)
                }
                it.copy(updateTime = System.nanoTime())
            }
            WanHelper.setSearchHistory(_uiState.value.searchHistoryResult)
        }
    }

    fun onWebBookmark(isAdd: Boolean, text: String) {
        viewModelScope.launch {
            _uiState.update {
                if (it.webBookmarkResult.contains(text)) {
                    it.webBookmarkResult.remove(text)
                }
                if (isAdd) {
                    it.webBookmarkResult.add(0, text)
                }
                it.copy(updateTime = System.nanoTime())
            }
            WanHelper.setWebBookmark(_uiState.value.webBookmarkResult)
        }
    }

    fun onWebHistory(isAdd: Boolean, text: String) {
        viewModelScope.launch {
            _uiState.update {
                if (it.webHistoryResult.contains(text)) {
                    it.webHistoryResult.remove(text)
                }
                if (isAdd) {
                    it.webHistoryResult.add(0, text)
                }
                it.copy(updateTime = System.nanoTime())
            }
            WanHelper.setWebHistory(_uiState.value.webHistoryResult)
        }
    }

}