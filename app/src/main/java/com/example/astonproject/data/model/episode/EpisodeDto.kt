package com.example.astonproject.data.model.episode

data class EpisodeDto(
    val info: EpisodeInfoDto?,
    val results: List<EpisodeResultDto>
)