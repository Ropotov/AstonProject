package com.example.astonproject.episode.data.repository


import com.example.astonproject.character.domain.model.CharacterResult
import com.example.astonproject.episode.data.mapper.EpisodeMapper
import com.example.astonproject.episode.data.network.EpisodeApiService
import com.example.astonproject.episode.domain.model.Episode
import com.example.astonproject.episode.domain.model.EpisodeResult
import com.example.astonproject.episode.domain.reposiitory.EpisodeRepository
import javax.inject.Inject

class EpisodeRepositoryImpl @Inject constructor(
    private val apiService: EpisodeApiService,
    private val mapper: EpisodeMapper
) : EpisodeRepository {

    override suspend fun getEpisode(page: Int, name: String, episode: String): Episode {
        val episodes = apiService.getEpisode(page, name, episode)
        return mapper.mapEpisodeDtoToEpisode(episodes)
    }

    override suspend fun getDetailEpisode(id: Int): EpisodeResult {
        val detailEpisode = apiService.getDetailEpisode(id)
        return mapper.mapResultsDtoToResults(detailEpisode)
    }

    override suspend fun getListCharacter(id: String): List<CharacterResult> {
        return apiService.getListCharacter(id)
    }
}