package com.example.astonproject.episode.presentation.episode

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.astonproject.episode.domain.model.EpisodeInfo
import com.example.astonproject.episode.domain.model.EpisodeResult
import com.example.astonproject.episode.domain.useCases.GetEpisodesUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.stateIn
import retrofit2.HttpException
import javax.inject.Inject

class EpisodeViewModel @Inject constructor(
    private val getEpisodesUseCase: GetEpisodesUseCase
) : ViewModel() {

    var episodeFlow: Flow<PagingData<EpisodeResult>> = emptyFlow()
    val episodeCount: MutableLiveData<EpisodeInfo> = MutableLiveData()

    fun load(name: String, episode: String) {
        episodeFlow = Pager(PagingConfig(pageSize = 1)) {
            getEpisodesUseCase.getEpisodes(name, episode)
        }.flow.cachedIn(viewModelScope)
            .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())
    }
    suspend fun loadCount(name: String, episode: String){
        try {
            episodeCount.value = getEpisodesUseCase.getEpisodeCount(1, name, episode).info
        }catch (e:HttpException){
            episodeCount.value = EpisodeInfo(0,"0",0,"0")
        }
    }
}