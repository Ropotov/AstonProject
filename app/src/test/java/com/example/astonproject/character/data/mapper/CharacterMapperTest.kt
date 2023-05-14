package com.example.astonproject.character.data.mapper

import com.example.astonproject.character.data.model.character.CharacterLocationDto
import com.example.astonproject.character.domain.model.CharacterLocation
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CharacterMapperTest {

    private lateinit var characterLocationDto: CharacterLocationDto
    private lateinit var characterLocation: CharacterLocation
    private val test = "test"


    @Before
    fun init() {
        characterLocationDto = CharacterLocationDto(
            name = test,
            url = test
        )
        characterLocation = CharacterLocation(
            name = test,
            url = test
        )
    }

    @Test
    fun mapCharacterLocationDtoToCharacterLocationTest() {
        val result = CharacterMapper().mapLocationDtoToLocation(characterLocationDto)
        assertEquals(characterLocation, result)
    }
}