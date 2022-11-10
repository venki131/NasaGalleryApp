package com.example.githubtrendingrepositories.presentation.state

import com.example.nasagalleryapp.domain.data.NasaGalleryDataItem

data class NasaGalleryState(
    val nasaGalleryList: ArrayList<NasaGalleryDataItem>? = arrayListOf(),
    val isLoading: Boolean = false,
    val error: String? = null
)