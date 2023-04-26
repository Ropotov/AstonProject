package com.example.astonproject.character.data.database

import androidx.room.*
import com.example.astonproject.character.data.database.model.CharacterResultDbModel

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(list: List<CharacterResultDbModel>)

    @Query("SELECT * FROM character")
    fun getListCharacterResult(): List<CharacterResultDbModel>

    @Query("DELETE FROM character")
    suspend fun clear()

    @Transaction
    suspend fun refresh(list: List<CharacterResultDbModel>) {
        clear()
        save(list)
    }

}