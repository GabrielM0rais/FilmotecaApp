package com.example.filmotecaapp.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.filmotecaapp.data.model.User
import com.example.filmotecaapp.domain.model.RegistratoionViewParams

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val username: String,
    val password: String,
)

fun RegistratoionViewParams.toUserEntity(): UserEntity {
    return UserEntity(
        username = this.username,
        password = this.password,
    )
}

fun UserEntity.toUser(): User {
    return User(
        id = this.id,
        username = this.username,
    )
}
