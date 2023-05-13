package com.example.astonproject.episode.data.paging

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.astonproject.episode.domain.model.EpisodeResult
import com.example.astonproject.episode.domain.reposiitory.EpisodeRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class EpisodePagingSource @Inject constructor(
    private val repository: EpisodeRepository,
    private val application: Application,
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
            if (hasConnected(application.applicationContext)) {
                val episodeResult = repository.getEpisode(page, name, episode).results
                resultDate.addAll(episodeResult)
                LoadResult.Page(
                    data = resultDate,
                    prevKey = if (page == 1) null else -1,
                    nextKey = page.plus(1)
                )
            } else {
                val episodeResult =
                    if (name == "" && episode == "") {
                        repository.getEpisodeListInDb()
                    } else {
                        repository.getEpisodeListInDb(name, episode)
                    }
                resultDate.addAll(episodeResult)
                if (resultDate.isEmpty()) {
                    LoadResult.Error(IOException())
                } else {
                    LoadResult.Page(
                        data = resultDate,
                        prevKey = null,
                        nextKey = null
                    )
                }
            }

        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    @Suppress("DEPRECATION")
    private fun hasConnected(context: Context): Boolean {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = manager.activeNetworkInfo
        return network != null && network.isConnected
    }
}