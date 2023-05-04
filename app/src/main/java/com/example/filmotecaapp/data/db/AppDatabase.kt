package com.example.filmotecaapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.filmotecaapp.data.db.dao.MovieDao
import com.example.filmotecaapp.data.db.dao.UserDao

@Database(entities = [UserEntity::class, MovieEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun movieDao(): MovieDao
}