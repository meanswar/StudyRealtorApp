package com.nikitosii.studyrealtorapp.domain.repository

import com.nikitosii.studyrealtorapp.core.domain.repository.ImageRepo
import com.nikitosii.studyrealtorapp.core.domain.repository.impl.ImageRepoImpl
import com.nikitosii.studyrealtorapp.core.source.db.dao.ImageDataDao
import com.nikitosii.studyrealtorapp.core.source.net.NetworkErrorHandler
import com.nikitosii.studyrealtorapp.core.source.net.api.image.ImageApi
import com.nikitosii.studyrealtorapp.core.source.net.model.base.BaseResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.image.ImageResponseApi
import com.nikitosii.studyrealtorapp.util.ImageDataTestUtils
import com.nikitosii.studyrealtorapp.util.TestConstants.ANY_TEXT
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ImageRepoTest {

    private lateinit var repo: ImageRepo

    private lateinit var dao: ImageDataDao
    private lateinit var api: ImageApi
    private lateinit var networkErrorHandler: NetworkErrorHandler

    @Before
    fun setup() {
        dao = mockk()
        api = mockk(relaxed = true)
        networkErrorHandler = NetworkErrorHandler(mockk(relaxed = true))

        repo = ImageRepoImpl(api, dao, networkErrorHandler)
    }

    @Test
    fun `get image test`() = runTest {
        coEvery { api.getPhoto(any()) } returns mockk<BaseResponseApi<ImageResponseApi>>(relaxed = true)
        repo.getImage(ANY_TEXT)

        coEvery { api.getPhoto(ANY_TEXT) }
    }

    @Test
    fun `insert image test`() = runTest {
        val initialData = ImageDataTestUtils.getExpectedImageData()
        val request = ImageDataTestUtils.getExpectedLocalImageData()

        coEvery { dao.insertImageData(request) } just Runs
        repo.insertImageData(initialData)

        coVerify { dao.insertImageData(request) }
    }

    @Test
    fun `remove all test`() = runTest {
        coEvery { dao.deleteAll() } just Runs
        repo.removeAll()

        coVerify { dao.deleteAll() }
    }

    @Test
    fun `get local image test`() = runTest {
        val expected = ImageDataTestUtils.getExpectedLocalImageData()

        coEvery { dao.getImage(any()) } returns expected
        repo.getLocalImage(ANY_TEXT)

        coVerify { dao.getImage(ANY_TEXT) }
    }
}