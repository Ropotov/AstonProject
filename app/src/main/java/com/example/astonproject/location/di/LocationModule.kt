package com.example.astonproject.location.di

import androidx.lifecycle.ViewModel
import com.example.astonproject.app.di.ViewModelKey
import com.example.astonproject.location.data.repository.LocationRepositoryImpl
import com.example.astonproject.location.data.network.LocationApiService
import com.example.astonproject.location.data.network.LocationApiService.Companion.LocationRetrofit
import com.example.astonproject.location.domain.repository.LocationRepository
import com.example.astonproject.location.presentation.LocationViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface LocationModule {

    @Binds
    fun bindLocationRepository(impl: LocationRepositoryImpl): LocationRepository

    @Binds
    @IntoMap
    @ViewModelKey(LocationViewModel::class)
    fun bindLocationViewModel(viewModel: LocationViewModel): ViewModel

    companion object {
        @Provides
        fun provideApiService(): LocationApiService {
            return LocationRetrofit.locationApi
        }
    }
}