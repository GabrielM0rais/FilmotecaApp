package com.example.filmotecaapp.domain.repository

import com.example.filmotecaapp.data.model.User
import com.example.filmotecaapp.domain.model.RegistratoionViewParams

interface UserRepository {
    suspend fun createUser(registrationViewParams: RegistratoionViewParams): Boolean

    suspend fun getUser(id: Long): User

    suspend fun login(username: String, password: String): User

    suspend fun  getUserByName(username: String): User?
}