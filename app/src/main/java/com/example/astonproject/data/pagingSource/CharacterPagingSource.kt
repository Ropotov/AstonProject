package com.example.astonproject.data.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.astonproject.data.repository.CharacterRepositoryImpl
import com.example.astonproject.domain.model.character.Result

class CharacterPagingSource : PagingSource<Int, Result>() {
    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            val repository = CharacterRepositoryImpl()
            val page = params.key ?: 1
            val response = repository.getCharacter(page)
            val data = response.results
            val responseDate = arrayListOf<Result>()
            responseDate.addAll(data)

            LoadResult.Page(
                data = responseDate,
                prevKey = if (page == 1) null else -1,
                nextKey = page.plus(1)
            )
        } catch (e: java.lang.Exception){
            LoadResult.Error(e)
        }
    }
}