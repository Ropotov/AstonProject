package com.example.astonproject.character.domain.repository

import com.example.astonproject.character.data.model.character.CharacterResultDto
import com.example.astonproject.character.domain.model.Character
import com.example.astonproject.character.domain.model.CharacterDetail
import com.example.astonproject.character.domain.model.CharacterResult
import com.example.astonproject.episode.domain.model.EpisodeResult
import io.reactivex.Single

interface CharacterRepository {
    suspend fun getCharacter(
        page: Int,
        name: String,
        status: String,
        gender: String,
        species: String
    ): Character

    fun getDetailCharacter(id: Int): Single<CharacterDetail>
    fun getDetailCharacterDb(id: Int): Single<CharacterDetail>
    fun getDetailEpisodeList(string: String): Single<List<EpisodeResult>>
    suspend fun insertCharacter(list: List<CharacterResultDto>)
    suspend fun getCharacterListInDb(): List<CharacterResult>
    suspend fun getCharacterListInDb(
        name: String,
        status: String,
        gender: String,
        species: String
    ): List<CharacterResult>

}