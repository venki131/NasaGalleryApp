package com.example.nasagalleryapp.presentation.view_model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubtrendingrepositories.presentation.state.NasaGalleryState
import com.example.nasagalleryapp.domain.repository.GalleryRepository
import com.example.nasagalleryapp.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NasaGalleryViewModel @Inject constructor(
    private val repository: GalleryRepository
) : ViewModel() {

    private var mutableLiveData: MutableLiveData<NasaGalleryState> = MutableLiveData()
    val liveData: MutableLiveData<NasaGalleryState> get() = mutableLiveData

    fun getGalleryList() {
        viewModelScope.launch {
            when (val result = repository.getGalleryInfo()) {
                is Resource.Loading -> {
                    mutableLiveData.value = NasaGalleryState(
                        nasaGalleryList = result.data,
                        isLoading = result.loadingStatus,
                        error = result.message
                    )
                }
                is Resource.Success -> {
                    mutableLiveData.value = NasaGalleryState(
                        nasaGalleryList = result.data,
                        isLoading = result.loadingStatus,
                        error = result.message
                    )
                }
                is Resource.Failure -> {
                    mutableLiveData.value = NasaGalleryState(
                        nasaGalleryList = result.data,
                        isLoading = result.loadingStatus,
                        error = result.message
                    )
                    Log.e("Error", result.message ?: "Unknown error")
                }
            }
        }
    }
}