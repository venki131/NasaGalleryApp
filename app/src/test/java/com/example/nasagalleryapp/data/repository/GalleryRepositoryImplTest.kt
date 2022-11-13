package com.example.nasagalleryapp.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.nasagalleryapp.TestCoroutineRule
import com.example.nasagalleryapp.data.remote.GalleryRepoApi
import com.example.nasagalleryapp.data.remote.NasaGalleryItemDto
import com.example.nasagalleryapp.domain.repository.GalleryRepository
import com.nhaarman.mockito_kotlin.given
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule
import retrofit2.HttpException

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class GalleryRepositoryImplTest {

    @Mock
    private lateinit var api: GalleryRepoApi

    lateinit var repository: GalleryRepository

    @Mock
    private lateinit var httpException: HttpException

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val initRule: MockitoRule = MockitoJUnit.rule()

    @Before
    fun setUp() {
        repository = GalleryRepositoryImpl(api, Dispatchers.IO)
    }

    @Test
    fun `call to gallery list success and returns list`() = runTest {
        given(api.getGalleryList()).willReturn(arrayListOf(sampleGalleryResponse))
        val result = repository.getGalleryInfo()
        assertNotNull(result)
        assertEquals(sampleGalleryResponse.copyright, result.data?.get(0)?.copyright)
    }

    @Test
    fun `call to gallery list failure and returns error message`() = runTest {
        given(api.getGalleryList()).willThrow(httpException)
        val result = repository.getGalleryInfo()
        assertNotNull(result)
        assertEquals("An unknown error occurred", result.message)
    }

    @After
    fun tearDown() {
    }

    private val sampleGalleryResponse = NasaGalleryItemDto(
        copyright = "ESA/HubbleNASA",
        date = "2019-12-01",
        explanation = "Sample Gallery",
        hdUrl = "https://apod.nasa.gov/apod/image/1912/M94_Hubble_1002.jpg",
        mediaType = "image",
        serviceVersion = "v1",
        title = "Starburst Galaxy M94 from Hubble",
        url = "https://apod.nasa.gov/apod/image/1912/M94_Hubble_960.jpg"
    )
}