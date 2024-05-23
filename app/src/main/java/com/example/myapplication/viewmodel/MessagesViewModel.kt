package com.example.myapplication.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.database.MessagesDatabase
import com.example.myapplication.entity.UserMessages
import com.example.myapplication.entity.MessagesEntity
import com.example.myapplication.repository.MessagesRepository
import com.example.myapplication.repository.OfflineMessagesRepository


class MessagesViewModel(context: Context) : ViewModel(){

    val userMessagesList = mutableListOf<UserMessages>()

    val messagesDetail = mutableListOf<MessagesEntity>()

    class MessageViewModelFactory constructor(private val context: Context ) : ViewModelProvider.Factory{

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MessagesViewModel::class.java)) {
                return MessagesViewModel(context) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }

    }

    private val itemsRepository: MessagesRepository by lazy {
        OfflineMessagesRepository(MessagesDatabase.getDatabase(context).messagesDao())
    }

    suspend fun saveItem(messagesEntity: MessagesEntity) {
        itemsRepository.insertItem(messagesEntity)
    }

    suspend fun updateItem(messagesEntity: MessagesEntity) {
        itemsRepository.updateItem(messagesEntity)
    }


}


