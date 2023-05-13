package com.example.astonproject.episode.domain.reposiitory


import com.example.astonproject.character.domain.model.CharacterResult
import com.example.astonproject.episode.data.model.EpisodeResultDto
import com.example.astonproject.episode.domain.model.Episode
import com.example.astonproject.episode.domain.model.EpisodeResult

interface EpisodeRepository {
    suspend fun getEpisode(page: Int, name: String, episode: String): Episode
    suspend fun getDetailEpisode(id: Int): EpisodeResult
    suspend fun getEpisodeListInDb(): List<EpisodeResult>
    suspend fun getEpisodeListInDb(name: String, season: String): List<EpisodeResult>
    suspend fun insertEpisode(list: List<EpisodeResultDto>)
    suspend fun getListCharacter(id: String): List<CharacterResult>
}