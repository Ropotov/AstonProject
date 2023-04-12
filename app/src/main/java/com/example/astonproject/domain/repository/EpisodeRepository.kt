package com.example.astonproject.domain.repository


import com.example.astonproject.domain.model.episode.Episode
import com.example.astonproject.domain.model.episode.EpisodeResult

interface EpisodeRepository {
    suspend fun getEpisode(page: Int): Episode
    suspend fun getDetailEpisode(id: Int): EpisodeResult
}