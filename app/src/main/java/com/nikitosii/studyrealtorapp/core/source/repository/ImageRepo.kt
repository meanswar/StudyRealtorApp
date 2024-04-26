package com.nikitosii.studyrealtorapp.core.source.repository

interface ImageRepo {

    suspend fun getImage(query: String): String?
}