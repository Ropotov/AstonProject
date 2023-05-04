package com.example.astonproject.character.data.repository

import com.example.astonproject.character.data.database.CharacterDao
import com.example.astonproject.character.data.mapper.CharacterMapper
import com.example.astonproject.character.data.network.CharacterApiService
import com.example.astonproject.character.data.network.CharacterApiServiceRX
import com.example.astonproject.character.domain.model.Character
import com.example.astonproject.character.domain.model.CharacterDetail
import com.example.astonproject.character.domain.model.CharacterResult
import com.example.astonproject.character.domain.repository.CharacterRepository
import com.example.astonproject.episode.domain.model.EpisodeResult
import com.example.astonproject.location.domain.model.LocationResult
import io.reactivex.Single
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val apiService: CharacterApiService,
    private val apiServiceRx: CharacterApiServiceRX,
    private val mapper: CharacterMapper,
    private val dao: CharacterDao
) : CharacterRepository {

    override suspend fun getCharacter(
        page: Int,
        name: String,
        status: String,
        gender: String
    ): Character {
        val character = apiService.getCharacter(page, name, status, gender)
        val list = mapper.mapCharacterDtoToCharacter(character)
        dao.save(mapper.mapListResultDtoToListDbModel(list.results))
        return list
    }

    override suspend fun saveListInDb(list: List<CharacterResult>) {
        dao.save(mapper.mapListResultDtoToListDbModel(list))
    }

    override suspend fun getLisCharacterResultInDb(): List<CharacterResult> {
        var list = emptyList<CharacterResult>()
        CoroutineScope(Dispatchers.IO).launch {
            list = (dao.getListCharacterResult()).map { it ->
                mapper.mapCharacterResultDbToCharacterResult(it)
            }
        }
        return list
    }

    override fun getDetailCharacter(id: Int): Single<CharacterDetail> {
        return apiServiceRx.getDetailCharacter(id).map { mapper.mapDetailDtoToDetail(it) }

    }

    override fun getDetailEpisodeList(string: String): Single<List<EpisodeResult>> {
        return apiServiceRx.getDetailEpisodeList(string)
    }

    override fun getDetailEpisode(id: Int): Single<EpisodeResult> {
        return apiServiceRx.getDetailEpisode(id)
    }

    override fun getDetailLocation(id: Int): Single<LocationResult> {
        return apiServiceRx.getDetailLocation(id)
    }
}