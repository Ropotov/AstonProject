package com.example.astonproject.location.data.mapper

import com.example.astonproject.location.model.LocationDto
import com.example.astonproject.location.model.LocationInfoDto
import com.example.astonproject.location.model.LocationResultDto
import com.example.astonproject.location.domain.model.Location
import com.example.astonproject.location.domain.model.LocationInfo
import com.example.astonproject.location.domain.model.LocationResult
import com.example.astonproject.location.presentation.model.LocationUiModel
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

    fun mapListResultsDtoToListResults(list: List<LocationResultDto>) = list.map {
        mapResultsDtoToResults(it)
    }

    fun mapLocationDtoToLocation(locationDto: LocationDto) = Location(
        info = mapInfoDtoToInfo(locationDto.info),
        results = mapListResultsDtoToListResults(locationDto.results)
    )

    fun mapLocationResultToLocationUi(locationResult: LocationResult): LocationUiModel =
        LocationUiModel(
            dimension = locationResult.dimension,
            id = locationResult.id,
            name = locationResult.name,
            type = locationResult.type,
            url = locationResult.url
        )


    companion object {
        private const val EMPTY_STRING = ""
        private const val EMPTY_NUMBER = 0
    }
}