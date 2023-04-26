package com.example.astonproject.location.data.network

import com.example.astonproject.location.model.LocationDto
import com.example.astonproject.location.model.LocationResultDto
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LocationApiService {

    @GET("location/")
    suspend fun getLocation(
        @Query("page") page: Int,
        @Query("name") name: String,
        @Query("type") type: String,
        @Query("dimension") dimension: String
    ): LocationDto

    @GET("location/{id}")
    suspend fun getDetailLocation(@Path("id") id: Int): LocationResultDto

    companion object {
        object LocationRetrofit {

            private const val URL = "https://rickandmortyapi.com/api/"

            private val okHttpClient by lazy {
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                    .build()
            }

            private val retrofit by lazy {
                Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            val locationApi: LocationApiService by lazy {
                retrofit.create(LocationApiService::class.java)
            }
        }
    }
}