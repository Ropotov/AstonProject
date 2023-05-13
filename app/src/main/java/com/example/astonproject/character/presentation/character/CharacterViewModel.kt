package com.example.astonproject.character.presentation.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.astonproject.character.domain.model.CharacterResult
import com.example.astonproject.character.domain.useCases.GetCharacterUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class CharacterViewModel @Inject constructor(
    private val getCharacterUseCase: GetCharacterUseCase,
) : ViewModel() {

    var characterFlow: Flow<PagingData<CharacterResult>> = emptyFlow()

    fun load(name: String, status: String, gender: String, species: String) {
        characterFlow = Pager(PagingConfig(pageSize = 1)) {
            getCharacterUseCase.getCharacter(name, status, gender, species)
        }.flow.stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())
    }
}
