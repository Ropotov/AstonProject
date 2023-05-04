package com.example.astonproject.app.di

import android.app.Application
import com.example.astonproject.app.App
import com.example.astonproject.character.di.CharacterModule
import com.example.astonproject.episode.di.EpisodeModule
import com.example.astonproject.episode.presentation.filter.EpisodeFilterFragment
import com.example.astonproject.episode.presentation.episode.EpisodeFragment
import com.example.astonproject.location.di.LocationModule
import com.example.astonproject.location.presentation.filter.LocationFilterFragment
import com.example.astonproject.location.presentation.location.LocationFragment
import com.example.astonproject.character.presentation.filter.CharacterFilterFragment
import com.example.astonproject.character.presentation.character.CharactersFragment
import com.example.astonproject.app.MainActivity
import com.example.astonproject.character.presentation.detail.CharacterDetailFragment
import com.example.astonproject.episode.presentation.detail.EpisodeDetailFragment
import com.example.astonproject.location.presentation.detail.LocationDetailFragment
import dagger.BindsInstance
import dagger.Component

@Component(modules = [CharacterModule::class, EpisodeModule::class, LocationModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)
    fun inject(fragment: LocationFilterFragment)
    fun inject(fragment: EpisodeFilterFragment)
    fun inject(fragment: CharacterFilterFragment)
    fun inject(fragment: LocationFragment)
    fun inject(fragment: EpisodeFragment)
    fun inject(fragment: CharactersFragment)
    fun inject(fragment: CharacterDetailFragment)
    fun inject(fragment: EpisodeDetailFragment)
    fun inject(fragment: LocationDetailFragment)
    fun inject(application: App)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ): AppComponent
    }
}
