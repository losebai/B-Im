package com.items.bim.repository

import com.items.bim.dao.McRecordDao
import com.items.bim.entity.McRecordEntity

interface McRecordRepository :  OfflineRepository<McRecordEntity, McRecordDao> {

}