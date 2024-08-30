package com.items.bim.repository.impl

import com.items.bim.dao.BaseDao
import com.items.bim.dao.KVMapDao
import com.items.bim.dao.McRecordDao
import com.items.bim.entity.KVMapEntity
import com.items.bim.entity.McRecordEntity
import com.items.bim.repository.KVMapRepository
import com.items.bim.repository.McRecordRepository

class OfflineKVRepository(private val kvDao: KVMapDao) : KVMapRepository {

    override fun getDao() = kvDao

    override suspend fun findByKey(key: String) = kvDao.findByKey(key)

}