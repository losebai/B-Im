package com.example.myapplication.repository

import com.example.myapplication.dao.McRecordDao
import com.example.myapplication.entity.McRecordEntity

interface McRecordRepository :  OfflineRepository<McRecordEntity, McRecordDao> {

}