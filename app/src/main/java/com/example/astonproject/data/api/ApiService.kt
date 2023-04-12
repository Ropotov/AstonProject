package com.example.astonproject.data.api

import com.example.astonproject.data.model.character.CharacterDto
import com.example.astonproject.data.model.character.ResultDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("character/")
    suspend fun getCharacter(@Query("page") page: Int): CharacterDto

    @GET("character/{id}")
    suspend fun getDetailCharacter(@Path("id") id: Int): ResultDto
}