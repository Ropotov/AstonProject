package com.example.astonproject.episode.presentation.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.astonproject.character.domain.model.CharacterResult
import com.example.astonproject.episode.domain.model.EpisodeResult
import com.example.astonproject.episode.domain.reposiitory.EpisodeRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class EpisodeDetailViewModel @Inject constructor(var repository: EpisodeRepository) : ViewModel() {

    var episodeDetail = MutableLiveData<EpisodeResult>()
    var listCharacters = MutableLiveData<List<CharacterResult>>()
    fun load(id: Int) {
        viewModelScope.launch() {
            episodeDetail.value = repository.getDetailEpisode(id)
        }
    }

    fun loadListCharacter(id: String) {
        viewModelScope.launch() {
            listCharacters.value = repository.getListCharacter(id)
        }
    }
}