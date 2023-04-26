package com.example.astonproject.character.domain.repository

import com.example.astonproject.character.data.database.model.CharacterResultDbModel
import com.example.astonproject.character.domain.model.CharacterResult
import com.example.astonproject.character.domain.model.Character
import com.example.astonproject.character.domain.model.CharacterDetail
import io.reactivex.Single

interface CharacterRepository {
    suspend fun getCharacter(page: Int, name:String, status:String, gender:String): Character
    suspend fun saveListInDb(list: List<CharacterResult>)
    suspend fun getLisCharacterResultInDb(): List<CharacterResult>
    fun getDetailCharacter(id: Int): Single<CharacterDetail>
}