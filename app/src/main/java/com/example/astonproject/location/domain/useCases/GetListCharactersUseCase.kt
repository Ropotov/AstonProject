package com.example.astonproject.location.domain.useCases

import com.example.astonproject.character.domain.model.CharacterResult
import com.example.astonproject.location.domain.repository.LocationRepository
import io.reactivex.Single
import javax.inject.Inject

class GetListCharactersUseCase @Inject constructor(
    private val repository: LocationRepository
) {
    fun getListCharacters(id: String): Single<List<CharacterResult>>{
        return repository.getListCharacter(id)
    }
}