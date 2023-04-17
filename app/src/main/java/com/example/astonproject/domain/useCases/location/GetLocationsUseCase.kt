package com.example.astonproject.domain.useCases.location

import com.example.astonproject.data.pagingSource.EpisodePagingSource
import com.example.astonproject.data.pagingSource.LocationPagingSource
import com.example.astonproject.domain.repository.EpisodeRepository
import com.example.astonproject.domain.repository.LocationRepository
import javax.inject.Inject

class GetLocationsUseCase @Inject constructor(
    private val repository: LocationRepository
) {
    fun getLocations(name: String, type: String, dimension: String): LocationPagingSource {
        return LocationPagingSource(repository, name, type, dimension)
    }
}
