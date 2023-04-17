package com.example.astonproject.presentation.screens.locationFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.astonproject.data.pagingSource.LocationPagingSource
import com.example.astonproject.domain.model.location.LocationResult
import com.example.astonproject.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class LocationViewModel @Inject constructor(
    private val repository: LocationRepository
) : ViewModel() {

    var locationFlow: Flow<PagingData<LocationResult>> = emptyFlow()

    fun load(name: String, type: String, dimension: String) {
        locationFlow = Pager(PagingConfig(pageSize = 1)) {
            LocationPagingSource(repository, name, type, dimension)
        }.flow.cachedIn(viewModelScope)
            .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())
    }
}
