package com.example.astonproject.episode.data.network

import com.example.astonproject.character.domain.model.CharacterResult
import com.example.astonproject.episode.data.model.EpisodeDto
import com.example.astonproject.episode.data.model.EpisodeResultDto
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EpisodeApiService {

    @GET("episode/")
    suspend fun getEpisode(
        @Query("page") page: Int,
        @Query("name") name: String,
        @Query("episode") episode: String
    ): EpisodeDto

    @GET("episode/{id}")
    suspend fun getDetailEpisode(@Path("id") id: Int): EpisodeResultDto

    @GET("character/{id}")
    suspend fun getListCharacter(@Path("id") id: String): List<CharacterResult>

    companion object {
        object EpisodeRetrofit {

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

            val episodeApiService: EpisodeApiService by lazy {
                retrofit.create(EpisodeApiService::class.java)
            }
        }
    }
}
