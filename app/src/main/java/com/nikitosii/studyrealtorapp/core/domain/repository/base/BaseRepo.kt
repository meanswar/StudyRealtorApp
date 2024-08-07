package com.nikitosii.studyrealtorapp.core.domain.repository.base

import android.util.LruCache
import com.nikitosii.studyrealtorapp.core.source.net.NetworkErrorHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import retrofit2.HttpException
import java.util.concurrent.Executors
import kotlin.coroutines.CoroutineContext

@Suppress("UNCHECKED_CAST")
open class BaseRepo(private val networkErrorHandler: NetworkErrorHandler) : CoroutineScope {

    override val coroutineContext: CoroutineContext =
        Executors.newSingleThreadExecutor().asCoroutineDispatcher()

    fun clearCache() {
        cache = createCache()
    }

    suspend fun <T> runWithErrorHandler(block: suspend () -> T) =
        networkErrorHandler.runWithErrorHandler(block)

    suspend fun parseException(e: HttpException)= networkErrorHandler.parseNetworkError(e)

    suspend fun <T> runCached(key: String, rewrite: Boolean = false, block: suspend () -> T) =
        if (rewrite) runCached(key, block) else cache[key] as? T ?: runCached(key, block)

    private suspend fun <T> runCached(key: String, block: suspend () -> T) =
        block().also { cache.put(key, it) }

    companion object {
        private const val DEFAULT_CACHE_LIMIT = 10
        private var cache = createCache()

        private fun createCache() = LruCache<String, Any>(DEFAULT_CACHE_LIMIT)
    }
}