package com.nikitosii.studyrealtorapp.core.source.repository.impl

import com.nikitosii.studyrealtorapp.core.source.db.dao.RequestDataDao
import com.nikitosii.studyrealtorapp.core.source.db.entity.RequestDataEntity
import com.nikitosii.studyrealtorapp.core.source.repository.RequestDataRepo
import javax.inject.Inject

class RequestDataRepoImpl @Inject constructor(
    private val dao: RequestDataDao
): RequestDataRepo {

    override suspend fun getData(requestId: Int): RequestDataEntity = dao.getData(requestId)

    override suspend fun saveData(data: RequestDataEntity) { dao.insert(data) }

    override suspend fun delete(requestId: Int) = dao.remove(requestId)

    override suspend fun removeAll() { dao.removeAll() }
}