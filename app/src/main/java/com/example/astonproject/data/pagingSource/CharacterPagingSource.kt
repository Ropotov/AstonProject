package com.example.astonproject.data.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.astonproject.data.repository.CharacterRepositoryImpl
import com.example.astonproject.domain.model.character.CharacterResult

class CharacterPagingSource : PagingSource<Int, CharacterResult>() {
    override fun getRefreshKey(state: PagingState<Int, CharacterResult>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterResult> {
        return try {
            val repository = CharacterRepositoryImpl()
            val page = params.key ?: 1
            val data = (repository.getCharacter(page)).results
            val resultDate = arrayListOf<CharacterResult>()
            resultDate.addAll(data)

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