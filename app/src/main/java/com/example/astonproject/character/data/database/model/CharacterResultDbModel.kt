package com.example.astonproject.character.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "character")
data class CharacterResultDbModel(
    var created: String,
    var gender: String,
    @PrimaryKey
    var id: Int,
    var image: String,
    var name: String,
    var species: String,
    var status: String,
    var type: String,
    var url: String
)