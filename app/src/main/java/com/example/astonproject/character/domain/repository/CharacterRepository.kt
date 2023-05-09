package com.example.astonproject.character.domain.repository

import com.example.astonproject.character.domain.model.Character
import com.example.astonproject.character.domain.model.CharacterDetail
import com.example.astonproject.episode.domain.model.EpisodeResult
import io.reactivex.Single

interface CharacterRepository {
    suspend fun getCharacter(page: Int, name: String, status: String, gender: String): Character
    fun getDetailCharacter(id: Int): Single<CharacterDetail>
    fun getDetailEpisodeList(string: String): Single<List<EpisodeResult>>
}