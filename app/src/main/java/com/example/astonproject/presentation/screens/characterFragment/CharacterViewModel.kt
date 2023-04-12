package com.example.astonproject.presentation.screens.characterFragment

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.astonproject.data.pagingSource.CharacterPagingSource

class CharacterViewModel : ViewModel() {

    val characterFlow = Pager(PagingConfig(pageSize = 1)){
        CharacterPagingSource()
    }.flow
}