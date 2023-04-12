package com.example.astonproject.presentation.screens.episodeFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.astonproject.data.pagingSource.EpisodePagingSource
import com.example.astonproject.domain.model.episode.EpisodeResult
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class EpisodeViewModel : ViewModel() {

    val episodeFlow: StateFlow<PagingData<EpisodeResult>> = Pager(PagingConfig(pageSize = 1)) {
        EpisodePagingSource()
    }.flow.cachedIn(viewModelScope).stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())
}