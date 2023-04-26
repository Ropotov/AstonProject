package com.example.astonproject.episode.domain.model

data class Episode(
    val info: EpisodeInfo,
    val results: List<EpisodeResult>
)