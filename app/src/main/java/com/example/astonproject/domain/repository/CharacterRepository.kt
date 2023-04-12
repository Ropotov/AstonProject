package com.example.astonproject.domain.repository

import com.example.astonproject.domain.model.character.Character
import com.example.astonproject.domain.model.character.Result

interface CharacterRepository {
    suspend fun getCharacter(page: Int): Character
    suspend fun getDetailCharacter(id: Int): Result
}