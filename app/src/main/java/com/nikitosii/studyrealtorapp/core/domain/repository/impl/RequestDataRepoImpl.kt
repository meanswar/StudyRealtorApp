package com.nikitosii.studyrealtorapp.core.domain.repository.impl

import com.nikitosii.studyrealtorapp.core.source.db.dao.RequestDataDao
import com.nikitosii.studyrealtorapp.core.source.db.entity.RequestDataEntity
import com.nikitosii.studyrealtorapp.core.domain.repository.RequestDataRepo
import javax.inject.Inject

class RequestDataRepoImpl @Inject constructor(
    private val dao: RequestDataDao
) : RequestDataRepo {

    override suspend fun getData(requestId: Int): RequestDataEntity =
        dao.getData(requestId) ?: RequestDataEntity(requestId, listOf())

    override suspend fun saveData(data: RequestDataEntity) {
        val old = dao.getData(data.requestId)
        val updated = old?.copy(propertiesIds = (old.propertiesIds + data.propertiesIds).toList())
        dao.insert(updated ?: data)
    }

    override suspend fun delete(requestId: Int) = dao.remove(requestId)

    override suspend fun removeAll() {
        dao.removeAll()
    }
}