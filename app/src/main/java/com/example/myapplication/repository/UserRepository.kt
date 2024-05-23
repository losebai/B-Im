package com.example.myapplication.repository

import com.example.myapplication.dao.MessagesDao
import com.example.myapplication.dao.UserDao
import com.example.myapplication.entity.UserEntity

interface  UserRepository : OfflineRepository<UserEntity, UserDao> {



}