package com.example.astonproject.character.data.repository

import com.example.astonproject.character.data.mapper.CharacterMapper
import com.example.astonproject.character.data.network.CharacterApiService
import com.example.astonproject.character.data.network.CharacterApiServiceRX
import com.example.astonproject.character.domain.model.Character
import com.example.astonproject.character.domain.model.CharacterDetail
import com.example.astonproject.character.domain.repository.CharacterRepository
import com.example.astonproject.episode.data.mapper.EpisodeMapper
import com.example.astonproject.episode.data.network.EpisodeApiServiceRX
import com.example.astonproject.episode.domain.model.EpisodeResult
import io.reactivex.Single
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val apiService: CharacterApiService,
    private val episodeApiServiceRx: EpisodeApiServiceRX,
    private val characterApiServiceRx: CharacterApiServiceRX,
    private val characterMapper: CharacterMapper,
    private val episodeMapper: EpisodeMapper,
) : CharacterRepository {

    override suspend fun getCharacter(
        page: Int, name: String, status: String, gender: String
    ): Character {
        val characterDto = apiService.getCharacter(page, name, status, gender)
        return characterMapper.mapCharacterDtoToCharacter(characterDto)
    }

    override fun getDetailCharacter(id: Int): Single<CharacterDetail> {
        return characterApiServiceRx.getDetailCharacter(id)
            .map { characterMapper.mapDetailDtoToDetail(it) }
    }

    override fun getDetailEpisodeList(string: String): Single<List<EpisodeResult>> {
        return episodeApiServiceRx.getDetailEpisodeList(string)
            .map { episodeMapper.mapListResultsDtoToListResults(it) }
    }
}