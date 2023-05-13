package com.example.astonproject.location.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.astonproject.location.data.db.dao.LocationDao
import com.example.astonproject.location.data.db.model.LocationResultDb

@Database(entities = [LocationResultDb::class], version = 1)
abstract class LocationDataBase : RoomDatabase() {
    abstract fun locationDao(): LocationDao

    companion object{
        private var database: LocationDataBase? = null
        private val ANY = Any()

        fun getMainDatabase(context: Context): LocationDataBase{
            synchronized(ANY){
                database?.let{
                    return it
                }
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LocationDataBase::class.java,
                    "locationDb"
                ).build()

                database = instance
                return instance
            }
        }
    }
}