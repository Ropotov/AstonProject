package com.example.astonproject.location.domain.useCases

import com.example.astonproject.location.domain.model.LocationResult
import com.example.astonproject.location.domain.repository.LocationRepository
import io.reactivex.Single
import javax.inject.Inject

class GetDetailLocationFromDbUseCase @Inject constructor(
    private val repository: LocationRepository
) {
    fun getDetailLocationFromDb(id: Int): Single<LocationResult> {
        return repository.getDetailLocationFromDb(id)
    }
}