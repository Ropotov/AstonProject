package com.example.astonproject.location.domain.model

data class Location(
    val info: LocationInfo,
    val results: List<LocationResult>
)
