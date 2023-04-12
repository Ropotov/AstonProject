package com.example.astonproject.data.repository

import com.example.astonproject.data.api.RetrofitInstance
import com.example.astonproject.data.mappers.CharacterMapper
import com.example.astonproject.domain.model.character.Character
import com.example.astonproject.domain.model.character.CharacterResult
import com.example.astonproject.domain.repository.CharacterRepository

class CharacterRepositoryImpl : CharacterRepository {

    private val apiService = RetrofitInstance.Api
    private val mapper = CharacterMapper()

    override suspend fun getCharacter(page: Int): Character {
        val character = apiService.getCharacter(page)
        return mapper.mapCharacterDtoToCharacter(character)
    }

    override suspend fun getDetailCharacter(id: Int): CharacterResult {
        val detailCharacter = apiService.getDetailCharacter(id)
        return mapper.mapResultsDtoToResults(detailCharacter)
    }
}