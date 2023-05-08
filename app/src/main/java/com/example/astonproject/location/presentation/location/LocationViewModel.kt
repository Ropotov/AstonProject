package com.example.astonproject.location.presentation.location

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.astonproject.location.domain.model.LocationInfo
import com.example.astonproject.location.domain.model.LocationResult
import com.example.astonproject.location.domain.useCases.GetLocationsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.stateIn
import retrofit2.HttpException
import javax.inject.Inject

class LocationViewModel @Inject constructor(
    private val getLocationsUseCase: GetLocationsUseCase
) : ViewModel() {

    var locationFlow: Flow<PagingData<LocationResult>> = emptyFlow()
    val locationCount: MutableLiveData<LocationInfo> = MutableLiveData()

    fun load(name: String, type: String, dimension: String) {
        locationFlow = Pager(PagingConfig(pageSize = 1)) {
            getLocationsUseCase.getLocations(name, type, dimension)
        }.flow.cachedIn(viewModelScope)
            .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())
    }

    suspend fun loadCount(name: String, type: String, dimension: String){
        try {
            locationCount.value = getLocationsUseCase.getLocationCount(name, type, dimension).info
        }catch (e: HttpException){
            locationCount.value = LocationInfo(0,"0",0,"0")
        }
    }
}
