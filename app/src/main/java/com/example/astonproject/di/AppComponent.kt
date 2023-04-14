package com.example.astonproject.di

import android.app.Application
import com.example.astonproject.presentation.screens.CharacterFilterFragment
import com.example.astonproject.presentation.screens.EpisodeFilterFragment
import com.example.astonproject.presentation.screens.LocationFilterFragment
import com.example.astonproject.presentation.screens.MainActivity
import com.example.astonproject.presentation.screens.characterFragment.CharactersFragment
import com.example.astonproject.presentation.screens.episodeFragment.EpisodeFragment
import com.example.astonproject.presentation.screens.locationFragment.LocationFragment
import dagger.BindsInstance
import dagger.Component

@Component(modules = [DataModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)
    fun inject(fragment: LocationFilterFragment)
    fun inject(fragment: EpisodeFilterFragment)
    fun inject(fragment: CharacterFilterFragment)
    fun inject(fragment: LocationFragment)
    fun inject(fragment: EpisodeFragment)
    fun inject(fragment: CharactersFragment)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ): AppComponent
    }
}
