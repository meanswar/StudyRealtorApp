package com.nikitosii.studyrealtorapp.core.source.repository

import com.nikitosii.studyrealtorapp.core.source.db.entity.RequestDataEntity

interface RequestDataRepo {

    /**
     * Retrieves data based on the provided request ID.
     *
     * @param requestId The ID of the request to retrieve data for.
     * @return The [RequestDataEntity] associated with the specified request ID.
     */
    suspend fun getData(requestId: Int): RequestDataEntity

    /**
     * Saves the provided request data into the local storage.
     *
     * @param requestDataEntity The [RequestDataEntity] to be saved.
     */
    suspend fun saveData(requestDataEntity: RequestDataEntity)

    /**
     * Deletes data associated with the provided request ID from the local storage.
     *
     * @param requestId The ID of the request data to be deleted.
     */
    suspend fun delete(requestId: Int)

    /**
     * Removes all data from the local storage.
     */
    suspend fun removeAll()
}