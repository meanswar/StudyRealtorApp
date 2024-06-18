package com.nikitosii.studyrealtorapp.di.db.dao

import com.nikitosii.studyrealtorapp.core.source.db.dao.RequestDataDao
import com.nikitosii.studyrealtorapp.core.source.db.entity.RequestDataEntity

object RequestDataDaoTest : RequestDataDao {

    private val requestDataList = mutableListOf<RequestDataEntity>()

    override fun getData(requestId: Int): RequestDataEntity? =
        requestDataList.firstOrNull { it.requestId == requestId }


    override fun insert(entity: RequestDataEntity) {
        requestDataList.add(entity)
    }

    override fun remove(requestId: Int) {
        requestDataList.removeIf { requestId == it.requestId }
    }

    override fun removeAll() {
        requestDataList.removeAll { true }
    }
}