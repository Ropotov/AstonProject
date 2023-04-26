package com.example.astonproject.location.domain.useCases

import com.example.astonproject.location.data.paging.LocationPagingSource
import com.example.astonproject.location.domain.repository.LocationRepository
import javax.inject.Inject

class GetLocationsUseCase @Inject constructor(
    private val repository: LocationRepository
) {
    fun getLocations(name: String, type: String, dimension: String): LocationPagingSource {
        return LocationPagingSource(repository, name, type, dimension)
    }
}
