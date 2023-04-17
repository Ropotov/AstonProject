package com.example.astonproject.domain.repository

import com.example.astonproject.domain.model.character.Character
import com.example.astonproject.domain.model.character.CharacterResult

interface CharacterRepository{
    suspend fun getCharacter(page: Int, name: String, status: String, gender: String): Character
    suspend fun getDetailCharacter(id: Int): CharacterResult
}