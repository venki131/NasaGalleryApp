package com.example.nasagalleryapp.di

import com.example.nasagalleryapp.data.repository.GalleryRepositoryImpl
import com.example.nasagalleryapp.domain.repository.GalleryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindGalleryRepository(
        galleryRepository: GalleryRepositoryImpl
    ): GalleryRepository
}
