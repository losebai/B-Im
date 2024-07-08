package com.items.bim.repository.impl

import com.items.bim.dao.McRecordDao
import com.items.bim.repository.McRecordRepository


class OfflineMcRecordRepository(private val mcRecordDao: McRecordDao) : McRecordRepository {
    override fun getDao() = mcRecordDao
}