package com.example.astonproject.character.domain.useCases

import com.example.astonproject.character.domain.repository.CharacterRepository
import javax.inject.Inject

class GetDetailCharacterUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    fun getDetailCharacter(id: Int) = repository.getDetailCharacter(id)
}