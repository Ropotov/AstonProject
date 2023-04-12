package com.example.astonproject.domain.model.episode

data class Episode(
    val info: EpisodeInfo,
    val results: List<EpisodeResult>
)