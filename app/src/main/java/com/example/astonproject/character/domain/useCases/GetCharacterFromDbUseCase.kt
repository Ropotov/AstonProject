package com.example.astonproject.character.domain.useCases

import com.example.astonproject.character.domain.model.CharacterResult
import com.example.astonproject.character.domain.repository.CharacterRepository
import javax.inject.Inject

class GetCharacterFromDbUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    suspend fun getCharacterFromDb(): List<CharacterResult> {
        return repository.getLisCharacterResultInDb()
    }
}