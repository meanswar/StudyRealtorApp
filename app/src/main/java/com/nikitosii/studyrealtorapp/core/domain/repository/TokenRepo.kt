package com.nikitosii.studyrealtorapp.core.domain.repository

interface TokenRepo {

    /**
     *  Saves token from tokens list in Constants to shared preferences for using via API
     */
    suspend fun generateNewToken()
}