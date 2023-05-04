package com.example.filmotecaapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.filmotecaapp.data.db.dao.UserDao

@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

}