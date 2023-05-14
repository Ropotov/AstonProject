package com.example.astonproject.character.data.repository

import com.example.astonproject.character.data.db.dao.CharacterDao
import com.example.astonproject.character.data.mapper.CharacterMapper
import com.example.astonproject.character.data.model.character.CharacterResultDto
import com.example.astonproject.character.data.network.CharacterApiService
import com.example.astonproject.character.domain.model.Character
import com.example.astonproject.character.domain.model.CharacterDetail
import com.example.astonproject.character.domain.model.CharacterResult
import com.example.astonproject.character.domain.repository.CharacterRepository
import com.example.astonproject.episode.data.mapper.EpisodeMapper
import com.example.astonproject.episode.data.network.EpisodeApiService
import com.example.astonproject.episode.domain.model.EpisodeResult
import io.reactivex.Single
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val apiService: CharacterApiService,
    private val episodeApiService: EpisodeApiService,
    private val characterMapper: CharacterMapper,
    private val episodeMapper: EpisodeMapper,
    private val dao: CharacterDao
) : CharacterRepository {

    override suspend fun getCharacter(
        page: Int, name: String, status: String, gender: String, species: String
    ): Character {
        val characterDto = apiService.getCharacter(page, name, status, gender, species)
        dao.deleteAllCharacters()
        insertCharacter(characterDto.results)
        return characterMapper.mapCharacterDtoToCharacter(characterDto)
    }

    override fun getDetailCharacter(id: Int): Single<CharacterDetail> {
        return apiService.getDetailCharacter(id)
            .map { characterMapper.mapDetailDtoToDetail(it) }
    }

    override fun getDetailCharacterDb(id: Int):Single<CharacterDetail> {
        return dao.getCharacterById(id).map { characterMapper.mapResultsDbToDetail(it)}
    }

    override fun getDetailEpisodeList(string: String): Single<List<EpisodeResult>> {
        return episodeApiService.getDetailEpisodeList(string)
            .map { episodeMapper.mapListResultsDtoToListResults(it) }
    }

    override suspend fun insertCharacter(list: List<CharacterResultDto>) {
        dao.insertCharacter(characterMapper.mapListResultsDtoToListResultsDdb(list))
    }

    override suspend fun getCharacterListInDb():
            List<CharacterResult> {
        return characterMapper.mapListResultsDbToListResults(
            dao.getAllCharacters()
        )
    }

    override suspend fun getCharacterListInDb(
        name: String,
        status: String,
        gender: String,
        species: String
    ): List<CharacterResult> {
        return characterMapper.mapListResultsDbToListResults(
            dao.getFilteredCharacters(name, status, gender, species)
        )
    }
}