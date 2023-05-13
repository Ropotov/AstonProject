package com.example.astonproject.episode.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "episode")
data class EpisodeResultDb(
    val air_date: String,
    val created: String,
    val episode: String,
    @PrimaryKey
    val id: Int,
    val name: String,
    val url: String
)