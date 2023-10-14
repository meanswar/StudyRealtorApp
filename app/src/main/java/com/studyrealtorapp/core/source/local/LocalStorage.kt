package com.studyrealtorapp.core.source.local

interface LocalStorage {
    fun clear()

    fun getToken(): String?
    fun saveToken(token: String?)
    fun hasToken(): Boolean
}