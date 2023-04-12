package com.example.astonproject.data.mappers

import com.example.astonproject.data.model.episode.EpisodeDto
import com.example.astonproject.data.model.episode.EpisodeInfoDto
import com.example.astonproject.data.model.episode.EpisodeResultDto
import com.example.astonproject.domain.model.episode.Episode
import com.example.astonproject.domain.model.episode.EpisodeInfo
import com.example.astonproject.domain.model.episode.EpisodeResult

class EpisodeMapper {

    private fun mapInfoDtoToInfo(infoDto: EpisodeInfoDto?) = EpisodeInfo(
        count = infoDto?.count ?: EMPTY_NUMBER,
        next = infoDto?.next ?: EMPTY_STRING,
        pages = infoDto?.pages ?: EMPTY_NUMBER,
        prev = infoDto?.prev ?: EMPTY_STRING,
    )

    fun mapResultsDtoToResults(resultDto: EpisodeResultDto?) = EpisodeResult(
        air_date = resultDto?.air_date ?: EMPTY_STRING,
        characters = resultDto?.characters ?: emptyList(),
        created = resultDto?.created ?: EMPTY_STRING,
        episode = resultDto?.episode ?: EMPTY_STRING,
        id = resultDto?.id ?: EMPTY_NUMBER,
        name = resultDto?.name ?: EMPTY_STRING,
        url = resultDto?.url ?: EMPTY_STRING
    )

    private fun mapListResultsDtoToListResults(list: List<EpisodeResultDto>) = list.map {
        mapResultsDtoToResults(it)
    }

    fun mapEpisodeDtoToEpisode(locationDto: EpisodeDto) = Episode(
        info = mapInfoDtoToInfo(locationDto.info),
        results = mapListResultsDtoToListResults(locationDto.results)
    )

    companion object {
        private const val EMPTY_STRING = ""
        private const val EMPTY_NUMBER = 0
    }
}