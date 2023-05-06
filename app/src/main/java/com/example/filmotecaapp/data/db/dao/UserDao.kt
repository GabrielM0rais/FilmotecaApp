package com.example.filmotecaapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.filmotecaapp.data.db.UserEntity
import com.example.filmotecaapp.data.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveUser(user: UserEntity)

    @Query("SELECT * FROM user WHERE id = :id")
    suspend fun getUser(id: Long): UserEntity

    @Query("SELECT * FROM user WHERE username = :username AND password = :password")
    suspend fun login(username: String, password: String): User

    @Query("SELECT * FROM user WHERE username = :username")
    suspend fun getUserByName(username: String): User?
}