package com.example.astonproject.character.domain.useCases

import android.app.Application
import com.example.astonproject.character.data.paging.CharacterPagingSource
import com.example.astonproject.character.domain.repository.CharacterRepository
import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(
    private val repository: CharacterRepository,
    private val application: Application
) {
    fun getCharacter(
        name: String, status: String, gender: String,species: String
    ): CharacterPagingSource {
        return CharacterPagingSource(repository, application, name, status, gender, species)
    }
}