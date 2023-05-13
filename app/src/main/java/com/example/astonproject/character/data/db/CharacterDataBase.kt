package com.example.astonproject.character.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.astonproject.character.data.db.dao.CharacterDao
import com.example.astonproject.character.data.db.model.CharacterResultDB


@Database(entities = [CharacterResultDB::class], version = 1)
abstract class CharacterDataBase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao

    companion object {
        private var database: CharacterDataBase? = null
        private val ANY = Any()

        fun getMainDatabase(context: Context): CharacterDataBase {
            synchronized(ANY) {
                database?.let {
                    return it
                }
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CharacterDataBase::class.java,
                    "characterDb"
                )
                    .build()
                database = instance
                return instance
            }
        }
    }
}