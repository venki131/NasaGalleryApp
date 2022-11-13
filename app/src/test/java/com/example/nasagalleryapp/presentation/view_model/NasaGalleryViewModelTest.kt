package com.example.nasagalleryapp.presentation.view_model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.nasagalleryapp.TestCoroutineRule
import com.example.nasagalleryapp.data.remote.NasaGalleryItemDto
import com.example.nasagalleryapp.data.remote.toNasaGallery
import com.example.nasagalleryapp.domain.data.NasaGalleryDataItem
import com.example.nasagalleryapp.domain.repository.GalleryRepository
import com.example.nasagalleryapp.domain.util.Resource
import com.example.nasagalleryapp.presentation.state.NasaGalleryState
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class NasaGalleryViewModelTest {

    @Mock
    lateinit var repository: GalleryRepository

    private lateinit var viewModel: NasaGalleryViewModel

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val initRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var galleryListObserver: Observer<NasaGalleryState>

    @Before
    fun setUp() {
        viewModel = NasaGalleryViewModel(repository)
        viewModel.liveData.observeForever(galleryListObserver)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `when fetching results fails then return an error`() = runTest {
        testCoroutineRule.runBlockingTest {
            viewModel.liveData.observeForever(galleryListObserver)
            `when`(repository.getGalleryInfo()).thenAnswer {
                Resource.Failure<String>("unknown error")
            }
            viewModel.getGalleryList()
            assertNotNull(viewModel.liveData.value)
            assertEquals("unknown error", viewModel.liveData.value?.error)
        }
    }

    @Test
    fun `return loading before making api call`() = runTest {
        testCoroutineRule.runBlockingTest {
            viewModel.liveData.observeForever(galleryListObserver)
            `when`(repository.getGalleryInfo()).thenAnswer {
                Resource.Loading<Boolean>(true)
            }
            viewModel.getGalleryList()
            assertNotNull(viewModel.liveData.value)
            assertEquals(true, viewModel.liveData.value?.isLoading)
        }
    }

    @Test
    fun `when fetching results success then return gallery list`() = runTest {
        testCoroutineRule.runBlockingTest {
            `when`(repository.getGalleryInfo()).thenAnswer {
                Resource.Success(arrayListOf(
                    NasaGalleryItemDto(
                        copyright = "ESA/HubbleNASA",
                        date = "2019-12-01",
                        explanation = "Sample Gallery",
                        hdUrl = "https://apod.nasa.gov/apod/image/1912/M94_Hubble_1002.jpg",
                        mediaType = "image",
                        serviceVersion = "v1",
                        title = "Starburst Galaxy M94 from Hubble",
                        url = "https://apod.nasa.gov/apod/image/1912/M94_Hubble_960.jpg"
                    )
                )
                    .map { it.toNasaGallery() } as ArrayList<NasaGalleryDataItem>)
            }
            advanceUntilIdle()
            viewModel.liveData.observeForever(galleryListObserver)
            viewModel.getGalleryList()
            assertNotNull(viewModel.liveData.value)
            assertEquals(
                sampleGalleryResponse.explanation,
                viewModel.liveData.value?.nasaGalleryList?.get(0)?.explanation
            )
            assertEquals(
                sampleGalleryResponse.copyright,
                viewModel.liveData.value?.nasaGalleryList?.get(0)?.copyright
            )
        }
    }


    private val sampleGalleryResponse = NasaGalleryDataItem(
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