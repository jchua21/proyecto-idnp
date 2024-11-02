package com.jean.touraqp.auth.domain.authentication.model

import com.jean.touraqp.auth.data.remote.dto.UserDto

data class User(
    val email: String = "",
    val name: String = "",
    val password: String = "",
    val username: String = ""
)
fun User.toUserDTO(): UserDto{
    return UserDto(
        email = email,
        name = name,
        password = password,
        username = username
    )
}

