package com.example.astonproject.location.data.mapper

import com.example.astonproject.location.data.db.model.LocationResultDb
import com.example.astonproject.location.data.model.LocationDto
import com.example.astonproject.location.data.model.LocationInfoDto
import com.example.astonproject.location.data.model.LocationResultDto
import com.example.astonproject.location.domain.model.Location
import com.example.astonproject.location.domain.model.LocationInfo
import com.example.astonproject.location.domain.model.LocationResult
import javax.inject.Inject

class LocationMapper @Inject constructor() {

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

    private fun mapResultsDtoToResultsDb(resultDto: LocationResultDto?) = LocationResultDb(
        created = resultDto?.created ?: EMPTY_STRING,
        dimension = resultDto?.dimension ?: EMPTY_STRING,
        id = resultDto?.id ?: EMPTY_NUMBER,
        name = resultDto?.name ?: EMPTY_STRING,
        type = resultDto?.type ?: EMPTY_STRING,
        url = resultDto?.url ?: EMPTY_STRING
    )

    private fun mapResultsDbToResults(resultDb: LocationResultDb) = LocationResult(
        created = resultDb.created,
        dimension = resultDb.dimension,
        id = resultDb.id,
        name = resultDb.name,
        type = resultDb.type,
        url = resultDb.url,
        residents = emptyList()
    )

    private fun mapListResultsDtoToListResults(list: List<LocationResultDto>) = list.map {
        mapResultsDtoToResults(it)
    }

    fun mapListResultsDtoToListResultsDb(list: List<LocationResultDto>) = list.map {
        mapResultsDtoToResultsDb(it)
    }

    fun mapListResultsDbToListResults(list: List<LocationResultDb>) = list.map {
        mapResultsDbToResults(it)
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