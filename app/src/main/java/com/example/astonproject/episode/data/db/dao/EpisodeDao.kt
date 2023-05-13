package com.example.astonproject.episode.data.db.dao

import androidx.room.*
import com.example.astonproject.episode.data.db.model.EpisodeResultDb

@Dao
interface EpisodeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisode(list: List<EpisodeResultDb>)

    @Query("SELECT * FROM episode")
    suspend fun getAllEpisode(): List<EpisodeResultDb>

    @Query("DELETE FROM episode")
    suspend fun deleteAllEpisode()

    @Transaction
    suspend fun refreshEpisode(list: List<EpisodeResultDb>) {
        deleteAllEpisode()
        insertEpisode(list)
    }

    @Query(
        "SELECT * FROM episode WHERE " +
                "name LIKE '%'|| :name || '%' AND " +
                "episode LIKE '%'|| :season || '%'"
    )
    suspend fun getFilteredEpisode(
        name: String,
        season: String,
    ): List<EpisodeResultDb>
}
