package com.example.astonproject.data.mappers

import com.example.astonproject.data.model.location.LocationDto
import com.example.astonproject.data.model.location.LocationInfoDto
import com.example.astonproject.data.model.location.LocationResultDto
import com.example.astonproject.domain.model.location.Location
import com.example.astonproject.domain.model.location.LocationInfo
import com.example.astonproject.domain.model.location.LocationResult

class LocationMapper {

    private fun mapInfoDtoToInfo(infoDto: LocationInfoDto?) = LocationInfo(
        count = infoDto?.count ?: EMPTY_NUMBER,
        next = infoDto?.next ?: EMPTY_STRING,
        pages = infoDto?.pages ?: EMPTY_NUMBER,
        prev = infoDto?.prev ?: EMPTY_STRING,
    )

    fun mapResultsDtoToResults(resultDto: LocationResultDto?) = LocationResult(
        created = resultDto?.created ?: EMPTY_STRING,
        dimension = resultDto?.dimension ?: EMPTY_STRING,
        id = resultDto?.id ?: EMPTY_NUMBER,
        name = resultDto?.name ?: EMPTY_STRING,
        residents = resultDto?.residents ?: emptyList(),
        type = resultDto?.type ?: EMPTY_STRING,
        url = resultDto?.url ?: EMPTY_STRING
    )

    private fun mapListResultsDtoToListResults(list: List<LocationResultDto>) = list.map {
        mapResultsDtoToResults(it)
    }

    fun mapLocationDtoToLocation(locationDto: LocationDto) = Location(
        info = mapInfoDtoToInfo(locationDto.info),
        results = mapListResultsDtoToListResults(locationDto.results)
    )

    companion object {
        private const val EMPTY_STRING = ""
        private const val EMPTY_NUMBER = 0
    }
}