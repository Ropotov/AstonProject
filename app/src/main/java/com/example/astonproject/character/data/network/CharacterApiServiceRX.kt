package com.example.astonproject.character.data.network

import com.example.astonproject.character.data.model.character.CharacterDetailDto
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface CharacterApiServiceRX {

    @GET("character/{id}")
    fun getDetailCharacter(@Path("id") id: Int): Single<CharacterDetailDto>

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