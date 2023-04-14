package com.example.astonproject.di

import com.example.astonproject.data.api.ApiService
import com.example.astonproject.data.api.RetrofitInstance
import com.example.astonproject.data.repository.CharacterRepositoryImpl
import com.example.astonproject.data.repository.EpisodeRepositoryImpl
import com.example.astonproject.data.repository.LocationRepositoryImpl
import com.example.astonproject.domain.repository.CharacterRepository
import com.example.astonproject.domain.repository.EpisodeRepository
import com.example.astonproject.domain.repository.LocationRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {
    @Binds
    fun bindCharacterRepository(impl: CharacterRepositoryImpl): CharacterRepository

    @Binds
    fun bindEpisodeRepository(impl: EpisodeRepositoryImpl): EpisodeRepository

    @Binds
    fun bindLocationRepository(impl: LocationRepositoryImpl): LocationRepository

    companion object {
        @Provides
        fun provideApiService(): ApiService {
            return RetrofitInstance.Api
        }
    }
}