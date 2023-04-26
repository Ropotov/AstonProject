package com.example.astonproject.character.data.model.character

data class CharacterDetailDto(
    val created: String,
    val episode: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: CharacterLocationDto,
    val name: String,
    val origin: CharacterOriginDto,
    val species: String,
    val status: String,
    val type: String,
    val url: String
)