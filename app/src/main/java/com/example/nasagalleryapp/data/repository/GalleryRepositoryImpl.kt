package com.example.nasagalleryapp.data.repository

import com.example.nasagalleryapp.data.remote.GalleryRepoApi
import com.example.nasagalleryapp.data.remote.toNasaGallery
import com.example.nasagalleryapp.domain.data.NasaGalleryDataItem
import com.example.nasagalleryapp.domain.repository.GalleryRepository
import com.example.nasagalleryapp.domain.util.Resource
import javax.inject.Inject

class GalleryRepositoryImpl @Inject constructor(
    private val api: GalleryRepoApi
) : GalleryRepository {

    override suspend fun getGalleryInfo(): Resource<ArrayList<NasaGalleryDataItem>> {
        return try {
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
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(
                e.message ?: "An unknown error occurred"
            )
        }
    }
}