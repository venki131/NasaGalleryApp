package com.example.nasagalleryapp.domain.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NasaGalleryDataItem(
    val copyright: String? = null,
    val date: String = "",
    val explanation: String = "",
    val hdUrl: String = "",
    val mediaType: String = "",
    val serviceVersion: String = "",
    val title: String = "",
    val url: String = ""
) : Parcelable
