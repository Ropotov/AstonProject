package com.example.astonproject.presentation.screens.locationFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.astonproject.data.pagingSource.CharacterPagingSource
import com.example.astonproject.data.pagingSource.LocationPagingSource
import kotlinx.coroutines.flow.StateFlow
import com.example.astonproject.domain.model.character.CharacterResult
import com.example.astonproject.domain.model.location.LocationResult
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class LocationViewModel : ViewModel() {

    val locationFlow: StateFlow<PagingData<LocationResult>> = Pager(PagingConfig(pageSize = 1)){
        LocationPagingSource()
    }.flow.cachedIn(viewModelScope).stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())
}