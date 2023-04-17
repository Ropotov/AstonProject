package com.example.astonproject.data.mappers

import com.example.astonproject.data.model.character.CharacterDto
import com.example.astonproject.data.model.character.CharacterInfoDto
import com.example.astonproject.data.model.character.CharacterLocationDto
import com.example.astonproject.data.model.character.CharacterResultDto
import com.example.astonproject.domain.model.character.Character
import com.example.astonproject.domain.model.character.CharacterInfo
import com.example.astonproject.domain.model.character.CharacterLocation
import com.example.astonproject.domain.model.character.CharacterResult
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

    fun mapResultsDtoToResults(resultDto: CharacterResultDto?) = CharacterResult(
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

    private fun mapListResultsDtoToListResults(list: List<CharacterResultDto>) = list.map {
        mapResultsDtoToResults(it)
    }


    fun mapCharacterDtoToCharacter(characterDto: CharacterDto) = Character(
        info = mapInfoDtoToInfo(characterDto.info),
        results = mapListResultsDtoToListResults(characterDto.results)
    )

    companion object {
        private const val EMPTY_STRING = ""
        private const val EMPTY_NUMBER = 0
    }
}