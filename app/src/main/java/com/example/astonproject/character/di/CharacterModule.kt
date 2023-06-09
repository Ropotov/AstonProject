package com.example.astonproject.character.di

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.astonproject.app.di.ViewModelKey
import com.example.astonproject.character.data.db.CharacterDataBase
import com.example.astonproject.character.data.db.dao.CharacterDao
import com.example.astonproject.character.data.network.CharacterApiService
import com.example.astonproject.character.data.network.CharacterApiService.Companion.CharacterRetrofit
import com.example.astonproject.character.data.repository.CharacterRepositoryImpl
import com.example.astonproject.character.domain.repository.CharacterRepository
import com.example.astonproject.character.presentation.character.CharacterViewModel
import com.example.astonproject.character.presentation.detail.CharacterDetailViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface CharacterModule {
    @Binds
    fun bindCharacterRepository(impl: CharacterRepositoryImpl): CharacterRepository

    @Binds
    @IntoMap
    @ViewModelKey(CharacterViewModel::class)
    fun bindCharacterViewModel(viewModel: CharacterViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CharacterDetailViewModel::class)
    fun bindCharacterDetailViewModel(viewModel: CharacterDetailViewModel): ViewModel


    companion object {
        @Provides
        fun provideApiService(): CharacterApiService {
            return CharacterRetrofit.characterApiService
        }

        @Provides
        fun provideCharacterDao(application: Application): CharacterDao {
            return CharacterDataBase.getMainDatabase(application).characterDao()
        }
    }
}