package com.nikitosii.studyrealtorapp.domain.repository

import com.nikitosii.studyrealtorapp.core.domain.repository.TokenRepo
import com.nikitosii.studyrealtorapp.core.domain.repository.impl.TokenRepoImpl
import com.nikitosii.studyrealtorapp.core.source.local.LocalStorage
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class TokenRepoTest {

    private lateinit var repo: TokenRepo
    private lateinit var storage: LocalStorage

    @Before
    fun setup() {
        storage = mockk(relaxed = true)
        repo = TokenRepoImpl(storage)
    }

    @Test
    fun `generate token test`() = runTest {
        coEvery { storage.saveToken(any()) } just Runs
        repo.generateNewToken()

        coVerify { storage.saveToken(any()) }
    }
}