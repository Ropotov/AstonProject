package com.example.astonproject.data.api

import com.example.astonproject.data.model.character.CharacterDto
import com.example.astonproject.data.model.character.CharacterResultDto
import com.example.astonproject.data.model.episode.EpisodeDto
import com.example.astonproject.data.model.episode.EpisodeResultDto
import com.example.astonproject.data.model.location.LocationDto
import com.example.astonproject.data.model.location.LocationResultDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("character/")
    suspend fun getCharacter(@Query("page") page: Int): CharacterDto

    @GET("character/{id}")
    suspend fun getDetailCharacter(@Path("id") id: Int): CharacterResultDto

    @GET("episode/")
    suspend fun getEpisode(@Query("page") page: Int): EpisodeDto

    @GET("episode/{id}")
    suspend fun getDetailEpisode(@Path("id") id: Int): EpisodeResultDto

    @GET("location/")
    suspend fun getLocation(@Query("page") page: Int): LocationDto

    @GET("location/{id}")
    suspend fun getDetailLocation(@Path("id") id: Int): LocationResultDto
}