package com.example.astonproject.location.data.db.dao

import androidx.room.*
import com.example.astonproject.location.data.db.model.LocationResultDb

@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(list: List<LocationResultDb>)

    @Query("SELECT * FROM location")
    suspend fun getAllLocation(): List<LocationResultDb>

    @Query("DELETE FROM location")
    suspend fun deleteAllLocation()

    @Transaction
    suspend fun refreshLocation(list: List<LocationResultDb>) {
        deleteAllLocation()
        insertLocation(list)
    }

    @Query(
        "SELECT * FROM location WHERE " +
                "name LIKE '%'|| :name ||'%' AND " +
                "type LIKE '%'|| :type ||'%' AND " +
                "dimension LIKE '%'|| :dimension ||'%'"
    )
    suspend fun getFilteredLocation(
        name: String,
        type: String,
        dimension: String
    ): List<LocationResultDb>
}