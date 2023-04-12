package com.example.astonproject.data.repository


import com.example.astonproject.data.api.RetrofitInstance
import com.example.astonproject.data.mappers.EpisodeMapper
import com.example.astonproject.domain.model.episode.Episode
import com.example.astonproject.domain.model.episode.EpisodeResult
import com.example.astonproject.domain.repository.EpisodeRepository

class EpisodeRepositoryImpl : EpisodeRepository {

    private val apiService = RetrofitInstance.Api
    private val mapper = EpisodeMapper()

    override suspend fun getEpisode(page: Int): Episode {
        val episode = apiService.getEpisode(page)
        return mapper.mapEpisodeDtoToEpisode(episode)
    }

    override suspend fun getDetailEpisode(id: Int): EpisodeResult {
        val detailEpisode = apiService.getDetailEpisode(id)
        return mapper.mapResultsDtoToResults(detailEpisode)
    }
}