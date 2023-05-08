package com.example.astonproject.episode.domain.reposiitory


import com.example.astonproject.character.domain.model.CharacterResult
import com.example.astonproject.episode.domain.model.Episode
import com.example.astonproject.episode.domain.model.EpisodeResult

interface EpisodeRepository {
    suspend fun getEpisode(page: Int, name: String, episode: String): Episode
    suspend fun getDetailEpisode(id: Int): EpisodeResult
    suspend fun getListCharacter(id: String): List<CharacterResult>
}