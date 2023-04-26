package com.example.astonproject.character.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.astonproject.character.data.database.model.CharacterResultDbModel

@Database(entities = [CharacterResultDbModel::class], version = 3)
abstract class CharacterDatabase : RoomDatabase() {
    companion object {
        private var db: CharacterDatabase? = null
        private val LOOK = Any()
        fun getInstance(context: Context): CharacterDatabase {
            synchronized(LOOK) {
                db?.let { return it }
                val instance = Room.databaseBuilder(
                    context, CharacterDatabase::class.java,
                    "character"
                )
                    .build()
                db = instance
                return instance
            }
        }
    }

    abstract fun characterDao(): CharacterDao
}