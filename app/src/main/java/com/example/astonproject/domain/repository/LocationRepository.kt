package com.example.astonproject.domain.repository


import com.example.astonproject.domain.model.location.Location
import com.example.astonproject.domain.model.location.LocationResult

interface LocationRepository {
    suspend fun getLocation(page: Int): Location
    suspend fun getDetailLocation(id: Int): LocationResult
}