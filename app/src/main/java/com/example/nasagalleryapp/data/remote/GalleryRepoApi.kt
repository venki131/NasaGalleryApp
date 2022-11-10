package com.example.nasagalleryapp.data.remote

import retrofit2.http.GET

interface GalleryRepoApi {

    @GET("galleryList")
    suspend fun getGalleryList(): ArrayList<NasaGalleryItemDto>
}