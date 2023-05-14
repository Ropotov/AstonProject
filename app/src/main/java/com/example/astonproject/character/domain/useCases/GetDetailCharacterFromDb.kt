package com.example.astonproject.character.domain.useCases

import com.example.astonproject.character.domain.model.CharacterDetail
import com.example.astonproject.character.domain.repository.CharacterRepository
import io.reactivex.Single
import javax.inject.Inject

class GetDetailCharacterFromDb @Inject constructor(
    private val repository: CharacterRepository
) {
    fun getDetailCharacterFromDb(id: Int): Single<CharacterDetail> {
        return repository.getDetailCharacterDb(id)
    }
}