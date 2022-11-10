package com.example.nasagalleryapp.data.remote

import android.os.Parcelable
import com.example.nasagalleryapp.domain.data.NasaGalleryDataItem
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class NasaGalleryItemDto(
    @SerializedName("copyright")
    @Expose
    val copyright: String? = null,
    @SerializedName("date")
    @Expose
    val date: String = "",
    @SerializedName("explanation")
    @Expose
    val explanation: String = "",
    @SerializedName("hdurl")
    @Expose
    val hdUrl: String = "",
    @SerializedName("media_type")
    @Expose
    val mediaType: String = "",
    @SerializedName("service_version")
    @Expose
    val serviceVersion: String = "",
    @SerializedName("title")
    @Expose
    val title: String = "",
    @SerializedName("url")
    @Expose
    val url: String = ""
) : Parcelable

fun NasaGalleryItemDto.toNasaGallery() = NasaGalleryDataItem(
    copyright = copyright,
    date = date,
    explanation = explanation,
    hdUrl = hdUrl,
    mediaType = mediaType,
    serviceVersion = serviceVersion,
    title = title,
    url = url
)