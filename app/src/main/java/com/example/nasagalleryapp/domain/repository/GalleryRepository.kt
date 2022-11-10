package com.example.nasagalleryapp.domain.repository

import com.example.nasagalleryapp.domain.data.NasaGalleryDataItem
import com.example.nasagalleryapp.domain.util.Resource

interface GalleryRepository {

    suspend fun getGalleryInfo(): Resource<ArrayList<NasaGalleryDataItem>>
}