package com.example.astonproject.presentation.screens.episodeFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.astonproject.data.pagingSource.EpisodePagingSource
import com.example.astonproject.domain.model.episode.EpisodeResult
import com.example.astonproject.domain.repository.CharacterRepository
import com.example.astonproject.domain.repository.EpisodeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class EpisodeViewModel @Inject constructor(
    private val repository: EpisodeRepository
) : ViewModel() {

    var episodeFlow: Flow<PagingData<EpisodeResult>> = emptyFlow()

    fun load(name: String, episode: String) {
        episodeFlow = Pager(PagingConfig(pageSize = 1)) {
            EpisodePagingSource(repository,name, episode)
        }.flow.cachedIn(viewModelScope)
            .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())
    }
}