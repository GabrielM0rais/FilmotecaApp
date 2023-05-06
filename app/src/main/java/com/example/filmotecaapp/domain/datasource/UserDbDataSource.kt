package com.example.filmotecaapp.domain.datasource

import com.example.filmotecaapp.data.db.dao.UserDao
import com.example.filmotecaapp.data.db.toUser
import com.example.filmotecaapp.data.db.toUserEntity
import com.example.filmotecaapp.data.model.User
import com.example.filmotecaapp.domain.model.RegistratoionViewParams
import com.example.filmotecaapp.domain.repository.UserRepository
import javax.inject.Inject

class UserDbDataSource @Inject constructor(
    private val userDao: UserDao
): UserRepository {
    override suspend fun createUser(registrationViewParams: RegistratoionViewParams): Boolean {
        val userEntity = registrationViewParams.toUserEntity()
        val existingUser = userDao.getUserByName(registrationViewParams.username)

        if (existingUser == null) {
            userDao.saveUser(userEntity)

            return true
        }

        return false
    }

    override suspend fun getUser(id: Long): User {
        return userDao.getUser(id).toUser()
    }

    override suspend fun login(username: String, password: String): User {
        return userDao.login(username, password)
    }

    override suspend fun getUserByName(username: String): User? {
        return userDao.getUserByName(username)
    }
}