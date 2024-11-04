package com.jean.touraqp.auth.domain.authentication.model

import com.jean.touraqp.auth.data.remote.dto.UserDto
import com.jean.touraqp.auth.ui.model.UserUI

data class UserDomain(
    val id : String? = null ,
    val email: String = "",
    val name: String = "",
    val password: String = "",
    val username: String = ""
)

fun UserDomain.toUserUI(): UserUI {
    return UserUI(
        id = id,
        email= email,
        name = name,
        username = username,
    )
}

fun UserDomain.toUserDTO(): UserDto{
    return UserDto(
        email = email,
        name = name,
        password = password,
        username = username
    )
}

