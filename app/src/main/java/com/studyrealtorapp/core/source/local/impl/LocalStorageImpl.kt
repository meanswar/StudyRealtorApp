package com.studyrealtorapp.core.source.local.impl

import com.orhanobut.hawk.Hawk
import com.studyrealtorapp.core.source.local.LocalStorage
import javax.inject.Inject

class LocalStorageImpl @Inject constructor(): LocalStorage {
    override fun clear() {
        Hawk.delete(KEY_TOKEN)
    }

    override fun getToken(): String? = Hawk.get<String>(KEY_TOKEN)

    override fun saveToken(token: String?) {
        Hawk.put(KEY_TOKEN, token)
    }

    override fun hasToken(): Boolean = Hawk.contains(KEY_TOKEN)

    companion object {
        const val KEY_TOKEN = "key-token"
    }
}