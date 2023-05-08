package com.example.astonproject.character.presentation.character

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.astonproject.character.domain.model.CharacterInfo
import com.example.astonproject.character.domain.model.CharacterResult
import com.example.astonproject.character.domain.useCases.GetCharacterUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.stateIn
import retrofit2.HttpException
import javax.inject.Inject

class CharacterViewModel @Inject constructor(
    private val getCharacterUseCase: GetCharacterUseCase,
) : ViewModel() {

    var characterFlow: Flow<PagingData<CharacterResult>> = emptyFlow()
    var character: MutableLiveData<CharacterInfo> = MutableLiveData()

    fun load(name: String, status: String, gender: String) {
        characterFlow = Pager(PagingConfig(pageSize = 1)) {
            getCharacterUseCase.getCharacter(name, status, gender)
        }.flow.cachedIn(viewModelScope)
            .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())
    }

    suspend fun loadCharacterCount(name: String, status: String, gender: String) {
        try {
            character.value =
                getCharacterUseCase.getCharacterCount(1, name, status, gender).info
        } catch (_: HttpException) {
            character.value = CharacterInfo(0, "0", 0, "0")
        }
    }
}
