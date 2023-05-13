package com.example.astonproject.character.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "character")
data class CharacterResultDB(
    var created: String,
    var gender: String,
    @PrimaryKey
    var id: Int,
    var image: String,
    var name: String,
    var species: String,
    var location: String,
    var status: String,
    var type: String,
    var url: String
)