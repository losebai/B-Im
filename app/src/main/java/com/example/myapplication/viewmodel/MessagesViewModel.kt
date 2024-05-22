package com.example.myapplication.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.myapplication.database.MessagesDatabase
import com.example.myapplication.entity.MessagesDetail
import com.example.myapplication.entity.MessagesEntity
import com.example.myapplication.repository.MessagesRepository
import com.example.myapplication.repository.OfflineMessagesRepository


class MessagesViewModel(context: Context) : ViewModel(){

    val messagesDetailList = mutableListOf<MessagesDetail>()

    private val itemsRepository: MessagesRepository by lazy {
        OfflineMessagesRepository(MessagesDatabase.getDatabase(context).messagesDao())
    }

    suspend fun saveItem(messagesEntity: MessagesEntity) {
        itemsRepository.insertItem(messagesEntity)
    }
}