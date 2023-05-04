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
    override fun createUser(registrationViewParams: RegistratoionViewParams) {
        val userEntity = registrationViewParams.toUserEntity()

        userDao.saveUser(userEntity)
    }

    override fun getUser(id: Long): User {
        return userDao.getUser(id).toUser()
    }

    override fun login(username: String, password: String): User {
        return userDao.login(username, password)
    }

}