package com.items.bim.repository

import com.items.bim.dao.KVMapDao
import com.items.bim.entity.KVMapEntity

interface KVMapRepository : OfflineRepository<KVMapEntity, KVMapDao> {

    suspend fun findByKey(key: String): KVMapEntity?

}