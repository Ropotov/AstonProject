package com.example.astonproject.location.model

data class LocationDto(
    val info: LocationInfoDto?,
    val results: List<LocationResultDto>
)
