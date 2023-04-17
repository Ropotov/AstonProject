package com.example.astonproject.domain.useCases.episode

import com.example.astonproject.data.pagingSource.EpisodePagingSource
import com.example.astonproject.domain.repository.EpisodeRepository
import javax.inject.Inject

class GetEpisodesUseCase @Inject constructor(
    private val repository: EpisodeRepository
) {
    fun getEpisodes(name: String, episode: String): EpisodePagingSource {
        return EpisodePagingSource(repository, name, episode)
    }
}
