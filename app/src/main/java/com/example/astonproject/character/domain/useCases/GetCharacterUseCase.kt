package com.example.astonproject.character.domain.useCases

import com.example.astonproject.character.data.paging.CharacterPagingSource
import com.example.astonproject.character.domain.repository.CharacterRepository
import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(
    private val repository: CharacterRepository,
) {
    fun getCharacter(
        name: String, status: String, gender: String
    ): CharacterPagingSource {
        return CharacterPagingSource(repository, name, status, gender)
    }

    suspend fun getCharacterCount(page: Int, name: String, status: String, gender: String) =
        repository.getCharacter(page, name, status, gender)
}