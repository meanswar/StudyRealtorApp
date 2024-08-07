package com.nikitosii.studyrealtorapp.core.source.local

class LocalStorageTest : LocalStorage {
    private var token: String? = null

    override fun clear() {
        token = null
    }

    override fun getToken(): String? = token

    override fun saveToken(token: String?) = run { this.token = token }

    override fun hasToken(): Boolean = token?.isNotEmpty() == true
}
