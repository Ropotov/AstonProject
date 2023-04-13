package com.example.astonproject.presentation.screens.characterFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import androidx.recyclerview.widget.RecyclerView
import com.example.astonproject.data.pagingSource.CharacterPagingSource
import com.example.astonproject.domain.model.character.CharacterResult
import kotlinx.coroutines.flow.*

class CharacterViewModel : ViewModel() {

    var characterFlow: Flow<PagingData<CharacterResult>> = emptyFlow()

    fun load(name: String, status: String, gender: String){
        characterFlow = Pager(PagingConfig(pageSize = 1)) {
            CharacterPagingSource(name, status, gender)
        }.flow.cachedIn(viewModelScope).stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())
    }
}