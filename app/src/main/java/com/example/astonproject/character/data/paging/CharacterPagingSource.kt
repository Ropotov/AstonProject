package com.example.astonproject.character.data.paging

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.astonproject.character.domain.model.CharacterResult
import com.example.astonproject.character.domain.repository.CharacterRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CharacterPagingSource @Inject constructor(
    private val repository: CharacterRepository,
    private val application: Application,
    private val name: String,
    private val status: String,
    private val gender: String,
    private val species: String
) : PagingSource<Int, CharacterResult>() {

    override fun getRefreshKey(state: PagingState<Int, CharacterResult>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterResult> {
        return try {
            val page = params.key ?: 1
            val resultDate = arrayListOf<CharacterResult>()
            if (hasConnected(application.applicationContext)) {
                val characterResult =
                    repository.getCharacter(page, name, status, gender, species).results
                resultDate.addAll(characterResult)
                LoadResult.Page(
                    data = resultDate,
                    prevKey = if (page == 1) null else -1,
                    nextKey = page.plus(1)
                )
            } else {
                val characterResult: List<CharacterResult> =
                    if (name == "" && status == "" && gender == "" && species == "") {
                        repository.getCharacterListInDb()
                    } else {
                        repository.getCharacterListInDb(name, status, gender, species)
                    }
                resultDate.addAll(characterResult)
                if (resultDate.isEmpty()){
                    LoadResult.Error(IOException())
                }else {
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