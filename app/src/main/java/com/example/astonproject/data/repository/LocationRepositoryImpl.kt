package com.example.astonproject.data.repository


import com.example.astonproject.data.api.ApiService
import com.example.astonproject.data.mappers.LocationMapper
import com.example.astonproject.domain.model.location.Location
import com.example.astonproject.domain.model.location.LocationResult
import com.example.astonproject.domain.repository.LocationRepository
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val mapper: LocationMapper
) : LocationRepository {

    override suspend fun getLocation(
        page: Int,
        name: String,
        type: String,
        dimension: String
    ): Location {
        val location = apiService.getLocation(page, name, type, dimension)
        return mapper.mapLocationDtoToLocation(location)
    }

    override suspend fun getDetailLocation(id: Int): LocationResult {
        val detailLocation = apiService.getDetailLocation(id)
        return mapper.mapResultsDtoToResults(detailLocation)
    }
}