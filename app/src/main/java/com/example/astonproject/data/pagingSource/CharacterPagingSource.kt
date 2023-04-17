package com.example.astonproject.data.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.astonproject.domain.model.character.CharacterResult
import com.example.astonproject.domain.repository.CharacterRepository
import javax.inject.Inject

class CharacterPagingSource @Inject constructor(
    private val repository: CharacterRepository,
    private val name: String,
    private val status: String,
    private val gender: String,
) : PagingSource<Int, CharacterResult>() {

    override fun getRefreshKey(state: PagingState<Int, CharacterResult>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterResult> {
        return try {
            val page = params.key ?: 1
            val resultDate = arrayListOf<CharacterResult>()
            resultDate.addAll((repository.getCharacter(page, name, status, gender)).results)
            LoadResult.Page(
                data = resultDate,
                prevKey = if (page == 1) null else -1,
                nextKey = page.plus(1)
            )
        } catch (e: java.lang.Exception) {
            LoadResult.Error(e)
        }
    }
}