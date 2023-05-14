package com.example.astonproject.location.data.repository


import com.example.astonproject.character.domain.model.CharacterResult
import com.example.astonproject.location.data.db.dao.LocationDao
import com.example.astonproject.location.data.mapper.LocationMapper
import com.example.astonproject.location.data.model.LocationResultDto
import com.example.astonproject.location.data.network.LocationApiService
import com.example.astonproject.location.domain.model.Location
import com.example.astonproject.location.domain.model.LocationResult
import com.example.astonproject.location.domain.repository.LocationRepository
import io.reactivex.Single
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val apiService: LocationApiService,
    private val mapper: LocationMapper,
    private val dao: LocationDao
) : LocationRepository {

    override suspend fun getLocation(
        page: Int,
        name: String,
        type: String,
        dimension: String
    ): Location {
        val location = apiService.getLocation(page, name, type, dimension)
        dao.deleteAllLocation()
        insertLocation(location.results)
        return mapper.mapLocationDtoToLocation(location)
    }

    override fun getDetailLocation(id: Int): Single<LocationResult> {
        return apiService.getDetailLocation(id).map { mapper.mapResultsDtoToResults(it) }
    }

    override fun getDetailLocationFromDb(id: Int): Single<LocationResult> {
        return dao.getDetailLocationById(id).map { mapper.mapResultsDbToResults(it) }
    }

    override fun getListCharacter(id: String): Single<List<CharacterResult>> {
        return apiService.getListCharacter(id)
    }

    override suspend fun getLocationListInDb(
        name: String,
        type: String,
        dimension: String
    ): List<LocationResult> {
        return mapper.mapListResultsDbToListResults(dao.getFilteredLocation(name, type, dimension))
    }

    override suspend fun insertLocation(list: List<LocationResultDto>) {
        dao.insertLocation(mapper.mapListResultsDtoToListResultsDb(list))
    }

    override suspend fun getLocationListInDb(): List<LocationResult> {
        return mapper.mapListResultsDbToListResults(dao.getAllLocation())
    }
}