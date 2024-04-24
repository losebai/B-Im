package com.example.myapplication.entity


data class Message(val message: String, val images: List<ImageEntity>);

data class Community(val imageEntity: ImageEntity, val message: Message);