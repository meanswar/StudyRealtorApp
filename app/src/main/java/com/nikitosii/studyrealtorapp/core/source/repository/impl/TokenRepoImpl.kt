package com.nikitosii.studyrealtorapp.core.source.repository.impl

import com.nikitosii.studyrealtorapp.core.source.local.LocalStorage
import com.nikitosii.studyrealtorapp.core.source.repository.TokenRepo
import com.nikitosii.studyrealtorapp.util.Constants
import javax.inject.Inject

class TokenRepoImpl @Inject constructor(private val storage: LocalStorage): TokenRepo {

    override suspend fun generateNewToken() {
        val id = Constants.tokensList.indexOf(storage.getToken())
        val newTokenId = if (Constants.tokensList.size - 1 != id) id + 1 else 0
        val newToken = Constants.tokensList[newTokenId]
        storage.saveToken(newToken)
    }
}