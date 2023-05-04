package com.example.astonproject.location.data.repository


import com.example.astonproject.character.domain.model.CharacterResult
import com.example.astonproject.location.data.mapper.LocationMapper
import com.example.astonproject.location.data.network.LocationApiService
import com.example.astonproject.location.data.network.LocationApiServiceRX
import com.example.astonproject.location.domain.model.Location
import com.example.astonproject.location.domain.model.LocationResult
import com.example.astonproject.location.domain.repository.LocationRepository
import io.reactivex.Single
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val apiService: LocationApiService,
    private val apiServiceRX: LocationApiServiceRX,
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

    override fun getDetailLocation(id: Int): Single<LocationResult> {
        return apiServiceRX.getDetailLocation(id).map { mapper.mapResultsDtoToResults(it) }
    }

    override fun getListCharacter(id: String): Single<List<CharacterResult>> {
        return apiServiceRX.getListCharacter(id)
    }
}