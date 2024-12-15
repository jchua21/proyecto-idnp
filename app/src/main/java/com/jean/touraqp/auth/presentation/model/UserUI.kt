package com.jean.touraqp.auth.presentation.model

import com.jean.touraqp.auth.domain.authentication.model.User

data class UserUI(
    val id: String? = null,
    val username: String = "",
    val name: String = "",
    val email: String = "",
    val password: String = "" ,
    val imageUrl: String = "https://utfs.io/f/AFyLEdVPOp24dt2Kt3A0gin2dHAV89k4PWEMefKrjO1mR3Zu"
)

fun UserUI.toUser(): User {
    return User(
        id = id,
        email = email,
        name = name,
        password = password,
        username = username,
    )
}