package com.nikitosii.studyrealtorapp.core.source.repository

import com.nikitosii.studyrealtorapp.core.source.db.entity.RequestDataEntity

interface RequestDataRepo {

    suspend fun getData(requestId: Int): RequestDataEntity

    suspend fun saveData(requestDataEntity: RequestDataEntity): RequestDataEntity

    suspend fun delete(requestId: Int)
}