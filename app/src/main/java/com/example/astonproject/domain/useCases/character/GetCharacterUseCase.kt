package com.example.astonproject.domain.useCases.character

import com.example.astonproject.data.pagingSource.CharacterPagingSource
import com.example.astonproject.domain.repository.CharacterRepository
import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    fun getCharacters(name: String, status: String, gender: String): CharacterPagingSource {
        return CharacterPagingSource(repository, name, status, gender)
    }
}