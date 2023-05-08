package com.example.astonproject.character.data.repository

import com.example.astonproject.character.data.database.CharacterDao
import com.example.astonproject.character.data.mapper.CharacterMapper
import com.example.astonproject.character.data.network.CharacterApiService
import com.example.astonproject.character.data.network.CharacterApiServiceRX
import com.example.astonproject.character.domain.model.Character
import com.example.astonproject.character.domain.model.CharacterDetail
import com.example.astonproject.character.domain.model.CharacterResult
import com.example.astonproject.character.domain.repository.CharacterRepository
import com.example.astonproject.episode.data.mapper.EpisodeMapper
import com.example.astonproject.episode.data.network.EpisodeApiServiceRX
import com.example.astonproject.episode.domain.model.EpisodeResult
import com.example.astonproject.location.data.mapper.LocationMapper
import com.example.astonproject.location.data.network.LocationApiServiceRX
import com.example.astonproject.location.domain.model.LocationResult
import io.reactivex.Single
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val apiService: CharacterApiService,
    private val episodeApiServiceRx: EpisodeApiServiceRX,
    private val characterApiServiceRx: CharacterApiServiceRX,
    private val locationApiServiceRX: LocationApiServiceRX,
    private val characterMapper: CharacterMapper,
    private val episodeMapper: EpisodeMapper,
    private val locationMapper: LocationMapper,
    private val dao: CharacterDao
) : CharacterRepository {

    override suspend fun getCharacter(
        page: Int,
        name: String,
        status: String,
        gender: String
    ): Character {
        val character = apiService.getCharacter(page, name, status, gender)
        val list = characterMapper.mapCharacterDtoToCharacter(character)
        dao.save(characterMapper.mapListResultDtoToListDbModel(list.results))
        return list
    }

    override suspend fun saveListInDb(list: List<CharacterResult>) {
        dao.save(characterMapper.mapListResultDtoToListDbModel(list))
    }

    override suspend fun getLisCharacterResultInDb(): List<CharacterResult> {
        var list = emptyList<CharacterResult>()
        CoroutineScope(Dispatchers.IO).launch {
            list = (dao.getListCharacterResult()).map {
                characterMapper.mapCharacterResultDbToCharacterResult(it)
            }
        }
        return list
    }

    override fun getDetailCharacter(id: Int): Single<CharacterDetail> {
        return characterApiServiceRx.getDetailCharacter(id)
            .map { characterMapper.mapDetailDtoToDetail(it) }

    }

    override fun getDetailEpisodeList(string: String): Single<List<EpisodeResult>> {
        return episodeApiServiceRx.getDetailEpisodeList(string)
            .map { episodeMapper.mapListResultsDtoToListResults(it) }
    }

    override fun getDetailEpisode(id: Int): Single<EpisodeResult> {
        return episodeApiServiceRx.getDetailEpisode(id)
            .map { episodeMapper.mapResultsDtoToResults(it) }
    }

    override fun getDetailLocation(id: Int): Single<LocationResult> {
        return locationApiServiceRX.getDetailLocation(id)
            .map { locationMapper.mapResultsDtoToResults(it) }
    }
}