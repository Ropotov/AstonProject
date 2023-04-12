package com.example.astonproject.data.mappers

import com.example.astonproject.data.model.character.CharacterDto
import com.example.astonproject.data.model.character.InfoDto
import com.example.astonproject.data.model.character.LocationDto
import com.example.astonproject.data.model.character.ResultDto
import com.example.astonproject.domain.model.character.Info
import com.example.astonproject.domain.model.character.Location
import com.example.astonproject.domain.model.character.Character
import com.example.astonproject.domain.model.character.Result


class CharacterMapper()  {

    private fun mapInfoDtoToInfo(infoDto: InfoDto) = Info(
        count = infoDto.count ?: EMPTY_NUMBER,
        next = infoDto.next ?: EMPTY_STRING,
        pages = infoDto.pages ?: EMPTY_NUMBER,
        prev = infoDto.prev ?: EMPTY_STRING,
    )

    private fun mapLocationDtoToLocation(locationDto: LocationDto) = Location(
        name = locationDto.name ?: EMPTY_STRING,
        url = locationDto.url ?: EMPTY_STRING
    )

    fun mapResultsDtoToResults(resultDto: ResultDto) = Result(
        created = resultDto.created ?: EMPTY_STRING,
        episode = resultDto.episode ?: emptyList(),
        gender = resultDto.gender ?: EMPTY_STRING,
        id = resultDto.id ?: EMPTY_NUMBER,
        image = resultDto.image ?: EMPTY_STRING,
        location = mapLocationDtoToLocation(resultDto.location),
        name = resultDto.name ?: EMPTY_STRING,
        species = resultDto.species ?: EMPTY_STRING,
        status = resultDto.status ?: EMPTY_STRING,
        type = resultDto.type ?: EMPTY_STRING,
        url = resultDto.url ?: EMPTY_STRING
    )

    private fun malListResultsDtoToListResults(list: List<ResultDto>) = list.map {
        mapResultsDtoToResults(it)
    }

    fun mapCharacterDtoToCharacter(characterDto: CharacterDto) = Character(
        info = mapInfoDtoToInfo(characterDto.info),
        results = malListResultsDtoToListResults(characterDto.results)
    )

    companion object {
        private const val EMPTY_STRING = ""
        private const val EMPTY_NUMBER = 0
    }
}