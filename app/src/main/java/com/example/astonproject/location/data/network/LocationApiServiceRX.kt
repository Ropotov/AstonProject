package com.example.astonproject.location.data.network

import com.example.astonproject.character.domain.model.CharacterResult
import com.example.astonproject.location.data.model.LocationResultDto
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface LocationApiServiceRX {

    @GET("location/{id}")
    fun getDetailLocation(@Path("id") id: Int): Single<LocationResultDto>

    @GET("character/{id}")
    fun getListCharacter(@Path("id") id: String): Single<List<CharacterResult>>

    companion object {
        object LocationRetrofitRX {

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
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }

            val locationApiServiceRX: LocationApiServiceRX by lazy {
                retrofit.create(LocationApiServiceRX::class.java)
            }
        }
    }
}