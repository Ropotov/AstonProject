package com.example.astonproject.episode.di

import androidx.lifecycle.ViewModel
import com.example.astonproject.app.di.ViewModelKey
import com.example.astonproject.episode.data.network.EpisodeApiService
import com.example.astonproject.episode.data.network.EpisodeApiService.Companion.EpisodeRetrofit
import com.example.astonproject.episode.data.repository.EpisodeRepositoryImpl
import com.example.astonproject.episode.domain.reposiitory.EpisodeRepository
import com.example.astonproject.episode.presentation.EpisodeViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface EpisodeModule {

    @Binds
    fun bindEpisodeRepository(impl: EpisodeRepositoryImpl): EpisodeRepository

    @Binds
    @IntoMap
    @ViewModelKey(EpisodeViewModel::class)
    fun bindEpisodeViewModel(viewModel: EpisodeViewModel): ViewModel

    companion object {
        @Provides
        fun provideApiService(): EpisodeApiService {
            return EpisodeRetrofit.episodeApiService
        }
    }
}