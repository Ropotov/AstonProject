package com.example.astonproject.episode.data.mapper

import com.example.astonproject.episode.data.db.model.EpisodeResultDb
import com.example.astonproject.episode.data.model.EpisodeDto
import com.example.astonproject.episode.data.model.EpisodeInfoDto
import com.example.astonproject.episode.data.model.EpisodeResultDto
import com.example.astonproject.episode.domain.model.Episode
import com.example.astonproject.episode.domain.model.EpisodeInfo
import com.example.astonproject.episode.domain.model.EpisodeResult
import javax.inject.Inject

class EpisodeMapper @Inject constructor(){

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

    private fun mapResultsDtoToResultsDb(resultDto: EpisodeResultDto?) = EpisodeResultDb(
        air_date = resultDto?.air_date ?: EMPTY_STRING,
        created = resultDto?.created ?: EMPTY_STRING,
        episode = resultDto?.episode ?: EMPTY_STRING,
        id = resultDto?.id ?: EMPTY_NUMBER,
        name = resultDto?.name ?: EMPTY_STRING,
        url = resultDto?.url ?: EMPTY_STRING
    )

    fun mapResultsDbToResults(resultDb: EpisodeResultDb) = EpisodeResult(
        air_date = resultDb.air_date ,
        characters = emptyList(),
        created = resultDb.created ,
        episode = resultDb.episode ,
        id = resultDb.id ,
        name = resultDb.name ,
        url = resultDb.url
    )

    fun mapListResultsDtoToListResults(list: List<EpisodeResultDto>) = list.map {
        mapResultsDtoToResults(it)
    }

    fun mapListResultsDtoToListResultsDb(list: List<EpisodeResultDto>) = list.map {
        mapResultsDtoToResultsDb(it)
    }

    fun mapListResultsDbToListResults(list: List<EpisodeResultDb>) = list.map {
        mapResultsDbToResults(it)
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