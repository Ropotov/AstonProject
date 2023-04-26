package com.example.astonproject.location.domain.repository


import com.example.astonproject.location.domain.model.Location
import com.example.astonproject.location.domain.model.LocationResult

interface LocationRepository {
    suspend fun getLocation(page: Int, name: String, type: String, dimension: String): Location
    suspend fun getDetailLocation(id: Int): LocationResult
}