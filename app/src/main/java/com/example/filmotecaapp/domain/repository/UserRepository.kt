package com.example.filmotecaapp.domain.repository

import com.example.filmotecaapp.data.model.User
import com.example.filmotecaapp.domain.model.RegistratoionViewParams

interface UserRepository {
    fun createUser(registrationViewParams: RegistratoionViewParams)

    fun getUser(id: Long): User

    fun login(username: String, password: String): User
}