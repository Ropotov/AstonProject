package com.example.astonproject.location.domain.useCases

import com.example.astonproject.location.domain.model.LocationResult
import com.example.astonproject.location.domain.repository.LocationRepository
import io.reactivex.Single
import javax.inject.Inject

class GetLocationDetailUseCase @Inject constructor(
    private val repository: LocationRepository
) {
    fun getDetailLocation(id: Int): Single<LocationResult> {
        return repository.getDetailLocation(id)
    }
}