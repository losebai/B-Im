package com.example.myapplication.repository.impl

import com.example.myapplication.dao.McRecordDao
import com.example.myapplication.repository.McRecordRepository


class OfflineMcRecordRepository(private val mcRecordDao: McRecordDao) : McRecordRepository {
    override fun getDao() = mcRecordDao
}