package com.example.nasagalleryapp.data.repository

import com.example.nasagalleryapp.data.remote.GalleryRepoApi
import com.example.nasagalleryapp.data.remote.toNasaGallery
import com.example.nasagalleryapp.di.IoDispatcher
import com.example.nasagalleryapp.domain.data.NasaGalleryDataItem
import com.example.nasagalleryapp.domain.repository.GalleryRepository
import com.example.nasagalleryapp.domain.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GalleryRepositoryImpl @Inject constructor(
    private val api: GalleryRepoApi,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : GalleryRepository {

    override suspend fun getGalleryInfo(): Resource<ArrayList<NasaGalleryDataItem>> {
        return try {
            withContext(dispatcher) {
                Resource.Loading(
                    loadingStatus = true,
                    data = null
                )
                Resource.Success(
                    data = api.getGalleryList()
                        .map {
                            it.toNasaGallery()
                        } as ArrayList<NasaGalleryDataItem>
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            withContext(dispatcher) {
                Resource.Failure(
                    e.localizedMessage ?: "An unknown error occurred"
                )
            }
        }
    }
}