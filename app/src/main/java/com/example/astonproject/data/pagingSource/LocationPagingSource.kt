package com.example.astonproject.data.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.astonproject.data.repository.LocationRepositoryImpl
import com.example.astonproject.domain.model.location.LocationResult

class LocationPagingSource : PagingSource<Int, LocationResult>() {
    override fun getRefreshKey(state: PagingState<Int, LocationResult>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LocationResult> {
        return try {
            val repository = LocationRepositoryImpl()
            val page = params.key ?: 1
            val data = (repository.getLocation(page)).results
            val resultDate = arrayListOf<LocationResult>()
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