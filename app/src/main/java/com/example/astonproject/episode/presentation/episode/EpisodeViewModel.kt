package com.example.astonproject.episode.presentation.episode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.astonproject.episode.domain.model.EpisodeResult
import com.example.astonproject.episode.domain.useCases.GetEpisodesUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class EpisodeViewModel @Inject constructor(
    private val getEpisodesUseCase: GetEpisodesUseCase
) : ViewModel() {

    var episodeFlow: Flow<PagingData<EpisodeResult>> = emptyFlow()

    fun load(name: String, episode: String) {
        episodeFlow = Pager(PagingConfig(pageSize = 1)) {
            getEpisodesUseCase.getEpisodes(name, episode)
        }.flow.stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())
    }
}