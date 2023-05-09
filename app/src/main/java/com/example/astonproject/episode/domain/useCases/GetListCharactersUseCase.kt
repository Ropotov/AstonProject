package com.example.astonproject.episode.domain.useCases

import com.example.astonproject.character.domain.model.CharacterResult
import com.example.astonproject.episode.domain.reposiitory.EpisodeRepository
import javax.inject.Inject

class GetListCharactersUseCase @Inject constructor(
    private val repository: EpisodeRepository
) {
    suspend fun getListCharacters(id: String): List<CharacterResult> {
        return repository.getListCharacter(id)
    }
}