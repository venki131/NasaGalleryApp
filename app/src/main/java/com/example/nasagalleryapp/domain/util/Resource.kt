package com.example.nasagalleryapp.domain.util

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null,
    val loadingStatus: Boolean = false
) {
    class Loading<T>(loadingStatus: Boolean, data: T? = null) : Resource<T>(data, loadingStatus = loadingStatus)
    class Success<T>(data: T?) : Resource<T>(data)
    class Failure<T>(message: String, data: T? = null) : Resource<T>(data, message = message)
}