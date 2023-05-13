package com.example.astonproject.location.data.paging

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.astonproject.location.domain.model.LocationResult
import com.example.astonproject.location.domain.repository.LocationRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class LocationPagingSource @Inject constructor(
    private val repository: LocationRepository,
    private val application: Application,
    private val name: String,
    private val type: String,
    private val dimension: String,
) : PagingSource<Int, LocationResult>() {
    override fun getRefreshKey(state: PagingState<Int, LocationResult>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LocationResult> {
        return try {
            val page = params.key ?: 1
            val resultDate = arrayListOf<LocationResult>()
            if (hasConnected(application.applicationContext)) {
                val locationResult = repository.getLocation(page, name, type, dimension).results
                resultDate.addAll(locationResult)
                LoadResult.Page(
                    data = resultDate,
                    prevKey = if (page == 1) null else -1,
                    nextKey = page.plus(1)
                )
            } else {
                val locationResult =
                    if (name == "" && type == "" && dimension == "") {
                        repository.getLocationListInDb()
                    } else {
                        repository.getLocationListInDb(name, type, dimension)
                    }
                resultDate.addAll(locationResult)
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
        } catch (e: java.lang.Exception) {
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