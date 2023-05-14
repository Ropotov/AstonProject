package com.example.astonproject.episode.data.repository


import com.example.astonproject.character.domain.model.CharacterResult
import com.example.astonproject.episode.data.db.dao.EpisodeDao
import com.example.astonproject.episode.data.mapper.EpisodeMapper
import com.example.astonproject.episode.data.model.EpisodeResultDto
import com.example.astonproject.episode.data.network.EpisodeApiService
import com.example.astonproject.episode.domain.model.Episode
import com.example.astonproject.episode.domain.model.EpisodeResult
import com.example.astonproject.episode.domain.reposiitory.EpisodeRepository
import javax.inject.Inject

class EpisodeRepositoryImpl @Inject constructor(
    private val apiService: EpisodeApiService,
    private val dao: EpisodeDao,
    private val mapper: EpisodeMapper
) : EpisodeRepository {

    override suspend fun getEpisode(page: Int, name: String, episode: String): Episode {
        val episodes = apiService.getEpisode(page, name, episode)
        dao.deleteAllEpisode()
        insertEpisode(episodes.results)
        return mapper.mapEpisodeDtoToEpisode(episodes)
    }

    override suspend fun getDetailEpisode(id: Int): EpisodeResult {
        val detailEpisode = apiService.getDetailEpisode(id)
        return mapper.mapResultsDtoToResults(detailEpisode)
    }

    override suspend fun getDetailEpisodeFromDb(id: Int): EpisodeResult {
        return mapper.mapResultsDbToResults(dao.getEpisodeById(id))
    }

    override suspend fun insertEpisode(list: List<EpisodeResultDto>) {
        dao.insertEpisode(mapper.mapListResultsDtoToListResultsDb(list))
    }

    override suspend fun getListCharacter(id: String): List<CharacterResult> {
        return apiService.getListCharacter(id)
    }

    override suspend fun getEpisodeListInDb(): List<EpisodeResult> {
        return mapper.mapListResultsDbToListResults(dao.getAllEpisode())
    }

    override suspend fun getEpisodeListInDb(name: String, season: String): List<EpisodeResult> {
        return mapper.mapListResultsDbToListResults(dao.getFilteredEpisode(name, season))
    }

}