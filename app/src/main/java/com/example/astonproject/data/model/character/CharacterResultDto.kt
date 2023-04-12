package com.example.astonproject.data.model.character

data class CharacterResultDto(
    var created: String?,
    var episode: List<String>?,
    var gender: String?,
    var id: Int?,
    var image: String?,
    var location: CharacterLocationDto,
    var name: String?,
    var species: String?,
    var status: String?,
    var type: String?,
    var url: String?
)