package com.example.astonproject.episode.domain.useCases

import com.example.astonproject.episode.data.paging.EpisodePagingSource
import com.example.astonproject.episode.domain.model.Episode
import com.example.astonproject.episode.domain.reposiitory.EpisodeRepository
import javax.inject.Inject

class GetEpisodesUseCase @Inject constructor(
    private val repository: EpisodeRepository
) {
    fun getEpisodes(name: String, episode: String): EpisodePagingSource {
        return EpisodePagingSource(repository, name, episode)
    }

    suspend fun getEpisodeCount(page: Int, name: String, episode: String): Episode {
        return repository.getEpisode(page, name, episode)
    }
}
