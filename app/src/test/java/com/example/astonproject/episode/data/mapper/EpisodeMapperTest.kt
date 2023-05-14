package com.example.astonproject.episode.data.mapper

import com.example.astonproject.episode.data.model.EpisodeInfoDto
import com.example.astonproject.episode.domain.model.EpisodeInfo
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class EpisodeMapperTest {

    private lateinit var episodeInfo: EpisodeInfo
    private lateinit var episodeInfoDto: EpisodeInfoDto

    @Before
    fun init() {
        episodeInfo = EpisodeInfo(
            next = "test",
            prev = "test",
            count = 1,
            pages = 1
        )
        episodeInfoDto = EpisodeInfoDto(
            next = "test",
            prev = "test",
            count = 1,
            pages = 1
        )
    }

    @Test
    fun mapEpisodeInfoDtoToEpisodeInfoTest() {
        val result = EpisodeMapper().mapInfoDtoToInfo(episodeInfoDto)
        Assert.assertEquals(episodeInfo, result)
    }
}