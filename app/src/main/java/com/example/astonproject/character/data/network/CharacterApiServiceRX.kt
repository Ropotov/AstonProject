package com.example.astonproject.character.data.network

import com.example.astonproject.character.data.model.character.CharacterDetailDto
import com.example.astonproject.episode.domain.model.EpisodeResult
import com.example.astonproject.location.domain.model.LocationResult
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterApiServiceRX {

    @GET("character/{id}")
    fun getDetailCharacter(@Path("id") id: Int): Single<CharacterDetailDto>

    @GET("episode/{id}")
    fun getDetailEpisodeList(@Path("id") list: String): Single<List<EpisodeResult>>

    @GET("episode/{id}")
    fun getDetailEpisode(@Path("id") id: Int): Single<EpisodeResult>

    @GET("location/{id}")
    fun getDetailLocation(@Path("id") id: Int): Single<LocationResult>


    companion object {
        object CharacterRetrofitRX {

            private const val URL = "https://rickandmortyapi.com/api/"

            private val okHttpClient by lazy {
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                    .build()
            }

            private val retrofitRX by lazy {
                Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
            }

            val characterApiServiceRX: CharacterApiServiceRX by lazy {
                retrofitRX.create(CharacterApiServiceRX::class.java)
            }
        }
    }
}