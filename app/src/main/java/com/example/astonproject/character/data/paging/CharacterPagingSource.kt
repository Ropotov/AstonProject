package com.example.astonproject.character.data.paging

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.astonproject.character.domain.model.CharacterResult
import com.example.astonproject.character.domain.repository.CharacterRepository
import javax.inject.Inject

class CharacterPagingSource @Inject constructor(
    private val repository: CharacterRepository,
    private val application: Application,
    private val name: String,
    private val status: String,
    private val gender: String
) : PagingSource<Int, CharacterResult>() {
    override fun getRefreshKey(state: PagingState<Int, CharacterResult>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterResult> {
        return try {
            val page = params.key ?: 1
            val resultDate = arrayListOf<CharacterResult>()
            if (hasConnected(application.applicationContext)) {
                resultDate.addAll(repository.getCharacter(page, name, status, gender).results)
            } else {
                val list = repository.getLisCharacterResultInDb()
                resultDate.addAll(list)
            }

            LoadResult.Page(
                data = resultDate,
                prevKey = if (page == 1) null else -1,
                nextKey = page.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    @Suppress("DEPRECATION")
    private fun hasConnected(context: Context): Boolean {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = manager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}