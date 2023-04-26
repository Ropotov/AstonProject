package com.example.astonproject.episode.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.astonproject.episode.domain.model.EpisodeResult
import com.example.astonproject.episode.domain.reposiitory.EpisodeRepository
import javax.inject.Inject

class EpisodePagingSource @Inject constructor(
    private val repository: EpisodeRepository,
    private val name: String,
    private val episode: String
) : PagingSource<Int, EpisodeResult>() {
    override fun getRefreshKey(state: PagingState<Int, EpisodeResult>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, EpisodeResult> {
        return try {
            val page = params.key ?: 1
            val resultDate = arrayListOf<EpisodeResult>()
            resultDate.addAll((repository.getEpisode(page, name, episode)).results)

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