package com.example.filmotecaapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.filmotecaapp.data.db.UserEntity
import com.example.filmotecaapp.data.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUser(user: UserEntity)

    @Query("SELECT * FROM user WHERE id = :id")
    fun getUser(id: Int): UserEntity

    @Query("SELECT * FROM user WHERE username = :username AND password = :password")
    fun login(username: String, password: String): User
}