package com.example.astonproject.character.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.astonproject.character.data.database.model.CharacterResultDbModel
import com.example.astonproject.character.domain.model.CharacterResult
import com.example.astonproject.character.domain.useCases.GetCharacterFromDbUseCase
import com.example.astonproject.character.domain.useCases.GetCharacterUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharacterViewModel @Inject constructor(
    private val getCharacterUseCase: GetCharacterUseCase,
) : ViewModel() {

    var characterFlow: Flow<PagingData<CharacterResult>> = emptyFlow()

    fun load(name: String, status: String, gender: String) {
        characterFlow = Pager(PagingConfig(pageSize = 1)) {
            getCharacterUseCase.getCharacter(name, status, gender)
        }.flow.cachedIn(viewModelScope)
            .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())
    }

}
