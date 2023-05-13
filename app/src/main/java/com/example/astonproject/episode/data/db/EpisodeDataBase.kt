package com.example.astonproject.episode.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.astonproject.episode.data.db.dao.EpisodeDao
import com.example.astonproject.episode.data.db.model.EpisodeResultDb


@Database(entities = [EpisodeResultDb::class], version = 2)
abstract class EpisodeDataBase : RoomDatabase() {
    abstract fun episodeDao(): EpisodeDao

    companion object {
        private var database: EpisodeDataBase? = null
        private val ANY = Any()

        fun getMainDatabase(context: Context): EpisodeDataBase {
            synchronized(ANY) {
                database?.let {
                    return it
                }
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EpisodeDataBase::class.java,
                    "episodeDb"

                )
                    .build()

                database = instance
                return instance
            }
        }
    }
}