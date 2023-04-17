package com.example.astonproject.di

import androidx.lifecycle.ViewModel
import com.example.astonproject.presentation.screens.characterFragment.CharacterViewModel
import com.example.astonproject.presentation.screens.episodeFragment.EpisodeViewModel
import com.example.astonproject.presentation.screens.locationFragment.LocationViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CharacterViewModel::class)
    fun bindCharacterViewModel(viewModel: CharacterViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EpisodeViewModel::class)
    fun bindEpisodeViewModel(viewModel: EpisodeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LocationViewModel::class)
    fun bindLocationViewModel(viewModel: LocationViewModel): ViewModel

}