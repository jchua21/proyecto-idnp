package com.jean.touraqp.auth.presentation.model

import com.jean.touraqp.auth.domain.authentication.model.User

data class UserUI(
    val id: String? = null,
    val username: String = "",
    val name: String = "",
    val email: String = "",
    val password: String = "" ,
)

fun UserUI.toUser(): User {
    return User(
        email = email,
        name = name,
        password = password,
        username = username,
    )
}