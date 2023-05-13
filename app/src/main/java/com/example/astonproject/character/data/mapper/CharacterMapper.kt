package com.example.astonproject.character.data.mapper

import com.example.astonproject.character.data.db.model.CharacterResultDB
import com.example.astonproject.character.data.model.character.*
import com.example.astonproject.character.domain.model.*
import javax.inject.Inject


class CharacterMapper @Inject constructor() {

    private fun mapInfoDtoToInfo(infoDto: CharacterInfoDto?) = CharacterInfo(
        count = infoDto?.count ?: EMPTY_NUMBER,
        next = infoDto?.next ?: EMPTY_STRING,
        pages = infoDto?.pages ?: EMPTY_NUMBER,
        prev = infoDto?.prev ?: EMPTY_STRING,
    )

    private fun mapLocationDtoToLocation(locationDto: CharacterLocationDto?) = CharacterLocation(
        name = locationDto?.name ?: EMPTY_STRING,
        url = locationDto?.url ?: EMPTY_STRING
    )

    private fun mapResultsDtoToResults(resultDto: CharacterResultDto?) = CharacterResult(
        created = resultDto?.created ?: EMPTY_STRING,
        episode = resultDto?.episode ?: emptyList(),
        gender = resultDto?.gender ?: EMPTY_STRING,
        id = resultDto?.id ?: EMPTY_NUMBER,
        image = resultDto?.image ?: EMPTY_STRING,
        location = mapLocationDtoToLocation(resultDto?.location),
        name = resultDto?.name ?: EMPTY_STRING,
        species = resultDto?.species ?: EMPTY_STRING,
        status = resultDto?.status ?: EMPTY_STRING,
        type = resultDto?.type ?: EMPTY_STRING,
        url = resultDto?.url ?: EMPTY_STRING
    )

    private fun mapResultsDtoToResultsDb(resultDto: CharacterResultDto?) = CharacterResultDB(
        created = resultDto?.created ?: EMPTY_STRING,
        location = resultDto?.location?.name.toString(),
        gender = resultDto?.gender ?: EMPTY_STRING,
        id = resultDto?.id ?: EMPTY_NUMBER,
        image = resultDto?.image ?: EMPTY_STRING,
        name = resultDto?.name ?: EMPTY_STRING,
        species = resultDto?.species ?: EMPTY_STRING,
        status = resultDto?.status ?: EMPTY_STRING,
        type = resultDto?.type ?: EMPTY_STRING,
        url = resultDto?.url ?: EMPTY_STRING

    )

    private fun mapResultsDbToResults(resultDB: CharacterResultDB) = CharacterResult(
        created = resultDB.created,
        location = CharacterLocation(resultDB.name, ""),
        gender = resultDB.gender,
        id = resultDB.id,
        image = resultDB.image,
        name = resultDB.name,
        species = resultDB.species,
        status = resultDB.status,
        type = resultDB.type,
        url = resultDB.url,
        episode = emptyList()
    )

    private fun mapListResultsDtoToListResults(list: List<CharacterResultDto>) = list.map {
        mapResultsDtoToResults(it)
    }

    fun mapListResultsDtoToListResultsDdb(list: List<CharacterResultDto>) = list.map {
        mapResultsDtoToResultsDb(it)
    }

    fun mapListResultsDbToListResults(list: List<CharacterResultDB>) = list.map {
        mapResultsDbToResults(it)
    }

    fun mapCharacterDtoToCharacter(characterDto: CharacterDto) = Character(
        info = mapInfoDtoToInfo(characterDto.info),
        results = mapListResultsDtoToListResults(characterDto.results)
    )

    fun mapDetailDtoToDetail(characterDetailDto: CharacterDetailDto): CharacterDetail {
        return CharacterDetail(
            created = characterDetailDto.created,
            gender = characterDetailDto.gender,
            name = characterDetailDto.name,
            species = characterDetailDto.species,
            status = characterDetailDto.status,
            episode = characterDetailDto.episode,
            type = characterDetailDto.type,
            image = characterDetailDto.image,
            location = mapLocationDtoToLocation(characterDetailDto.location),
            id = characterDetailDto.id,
            url = characterDetailDto.url,
            origin = mapOriginDtoToOrigin(characterDetailDto.origin),
        )
    }

    private fun mapOriginDtoToOrigin(originDto: CharacterOriginDto): CharacterOrigin {
        return CharacterOrigin(
            name = originDto.name,
            url = originDto.url
        )
    }

    companion object {
        private const val EMPTY_STRING = ""
        private const val EMPTY_NUMBER = 0
    }
}