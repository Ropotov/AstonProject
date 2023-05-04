package com.example.astonproject.location.domain.repository


import com.example.astonproject.character.domain.model.CharacterResult
import com.example.astonproject.location.domain.model.Location
import com.example.astonproject.location.domain.model.LocationResult
import io.reactivex.Single

interface LocationRepository {
    suspend fun getLocation(page: Int, name: String, type: String, dimension: String): Location
    fun getDetailLocation(id: Int): Single<LocationResult>
    fun getListCharacter(id: String): Single<List<CharacterResult>>
}