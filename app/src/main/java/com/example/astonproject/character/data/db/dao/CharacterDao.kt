package com.example.astonproject.character.data.db.dao

import androidx.room.*
import com.example.astonproject.character.data.db.model.CharacterResultDB

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(list: List<CharacterResultDB>)

    @Query("SELECT * FROM character")
    suspend fun getAllCharacters(): List<CharacterResultDB>

    @Query("DELETE FROM character")
    suspend fun deleteAllCharacters()

    @Transaction
    suspend fun refreshCharacters(list: List<CharacterResultDB>) {
        deleteAllCharacters()
        insertCharacter(list)
    }

    @Query(
        "SELECT * FROM character WHERE " +
                "name LIKE '%'|| :name || '%' AND " +
                "status LIKE '%'|| :status || '%' AND " +
                "gender LIKE :gender || '%' AND " +
                "species LIKE '%'|| :species || '%'"
    )
    suspend fun getFilteredCharacters(
        name: String?,
        status: String?,
        gender: String?,
        species: String?
    ): List<CharacterResultDB>
}
