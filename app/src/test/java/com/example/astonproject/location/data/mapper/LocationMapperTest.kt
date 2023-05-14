package com.example.astonproject.location.data.mapper

import com.example.astonproject.location.data.model.LocationInfoDto
import com.example.astonproject.location.domain.model.LocationInfo
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class LocationMapperTest {

    private lateinit var locationInfo: LocationInfo
    private lateinit var locationInfoDto: LocationInfoDto

    @Before
    fun init() {
        locationInfo = LocationInfo(
            next = "test",
            prev = "test",
            count = 1,
            pages = 1
        )
        locationInfoDto = LocationInfoDto(
            next = "test",
            prev = "test",
            count = 1,
            pages = 1
        )
    }

    @Test
    fun mapEpisodeInfoDtoToEpisodeInfoTest() {
        val result = LocationMapper().mapInfoDtoToInfo(locationInfoDto)
        Assert.assertEquals(locationInfo, result)
    }
}