package com.example.astonproject.character.data.network

import com.example.astonproject.character.data.model.character.CharacterDetailDto
import com.example.astonproject.character.data.model.character.CharacterDto
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterApiService {

    @GET("character/")
    suspend fun getCharacter(
        @Query("page") page: Int,
        @Query("name") name: String,
        @Query("status") status: String,
        @Query("gender") gender: String,
        @Query("species") species: String,
    ): CharacterDto

    @GET("character/{id}")
    fun getDetailCharacter(@Path("id") id: Int): Single<CharacterDetailDto>

    companion object {
        object CharacterRetrofit {

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
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
            }

            val characterApiService: CharacterApiService by lazy {
                retrofit.create(CharacterApiService::class.java)
            }
        }
    }
}