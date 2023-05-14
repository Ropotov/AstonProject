package com.example.astonproject.episode.domain.useCases

import com.example.astonproject.episode.domain.model.EpisodeResult
import com.example.astonproject.episode.domain.reposiitory.EpisodeRepository
import javax.inject.Inject

class GetDetailEpisodeFromDbUseCase @Inject constructor(
    private val repository: EpisodeRepository
) {
    suspend fun getDetailEpisodeFromDb(id: Int): EpisodeResult{
        return repository.getDetailEpisodeFromDb(id)
    }
}